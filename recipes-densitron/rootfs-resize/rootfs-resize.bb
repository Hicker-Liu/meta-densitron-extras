FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
SUMMARY = "Resizes rootfs to use all available space"
DESCRIPTION = " \
	Script and service for one-time expansion of rootfs, needed after flashing a wic file. \
	reference source: \
	https://github.com/embeddedartists/meta-ea/blob/ea-5.4.47/recipes-ea/ea-resizefs/ea-resizefs.bb \
"
LICENSE = "MIT"
LIC_FILES_CHKSUM="file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

RDEPENDS_${PN} = "e2fsprogs-resize2fs parted"

SRC_URI = " \
	file://LICENSE \
"

do_install () {
	install -d ${D}/opt
	install -d ${D}/opt/densitron
	install -d ${D}/opt/densitron/share
	install -m 0755 ${WORKDIR}/LICENSE  ${D}/opt/densitron/share//LICENSE
}


pkg_postinst_ontarget_${PN} () {
	# Locate mmc device with cmdline and extract device path
	mmc_part=
	mmc_device=

	mmc_part_number=
	mmc_part_start=

	mmc_part_off=
	mmc_part_size=
	mmc_total_size=
	mmc_free_size=

	for i in `cat /proc/cmdline`
	do
		if [[ $i =~ .*root=/dev/mmcblk.* ]]; then
                mmc_part=`echo $i | cut -d '=' -f 2`
                mmc_device=`echo $mmc_part | cut -d 'p' -f 1`
                mmc_part_number=`echo $mmc_part | cut -d 'p' -f 2`
        fi
	done

	if [[ -z "$mmc_device" ]]; then
		echo "Target device : not found mmc/sd device" >> /opt/densitron/rootfs-resizefs.log
		exit -1
	else
		echo "Target device : $mmc_device" >> /opt/densitron/rootfs-resizefs.log
	fi

	# Find last partition
	#mmc_part_number=$(parted $mmc_device -ms unit s p | tail -n 1 | cut -f 1 -d:)

	# Extract the partition's start sector
	mmc_part_start=$(parted $mmc_device -ms unit s p | grep "^${mmc_part_number}" | cut -f 2 -d:)

	mmc_part_off=`cat /sys/block/${mmc_device#/dev/}/${mmc_device#/dev/}p${mmc_part_number}/start`
	mmc_part_size=`cat /sys/block/${mmc_device#/dev/}/${mmc_device#/dev/}p${mmc_part_number}/size`
	mmc_total_size=`cat /sys/block/${mmc_device#/dev/}/size`
	mmc_free_size=`expr $mmc_total_size - $mmc_part_off - $mmc_part_size`

	# Skip resizing if close to max size already
	if [ $mmc_free_size -lt 10240 ]; then
		echo "Skipping resizing of ${mmc_device}p${mmc_part_number} - only $mmc_free_size blocks left" >> /opt/densitron/rootfs-resizefs.log
		exit 0
	fi

	mmc_part_old_size=`df -h ${mmc_device}p${mmc_part_number} | grep /dev/ |awk '{print $2}'`

	echo "Resizing ${mmc_device}p${mmc_part_number}: Old size $mmc_part_old_size" >> /opt/densitron/rootfs-resizefs.log

	# Remove the last partition
	parted $mmc_device -ms rm $mmc_part_number

	# Create a new partition with the old partition's start sector
	# but using 100% of the available space as size.
	parted $mmc_device -ms unit s mkpart primary $mmc_part_start 100%

	resize2fs ${mmc_device}p${mmc_part_number}

	mmc_part_new_size=`df -h ${mmc_device}p${mmc_part_number} | grep /dev/ |awk '{print $2}'`
	echo "Finished resizing ${mmc_device}p${mmc_part_number}: New size $mmc_part_new_size" >> /opt/densitron/rootfs-resizefs.log
}

PACKAGES = "${PN}"
FILES_${PN} = "/ /opt/densitron"
