FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
	file://quixant.png \
	file://kms.conf \
"

do_install_append() {
	install -m 0755 -d ${D}${sysconfdir}
        install -m 0644 ${WORKDIR}/kms.conf ${D}${sysconfdir}/kms.conf

	install -m 0755 -d ${D}/opt/densitron/share/densitron/
	dd if=/dev/urandom of=${D}/opt/densitron/share/densitron/template.file count=204800

	install -m 0755 -d ${D}${datadir}/densitron/
        install ${WORKDIR}/quixant.png ${D}${datadir}/densitron/quixant.png
}
