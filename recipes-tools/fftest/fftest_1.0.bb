DESCRIPTION = "the force feedback driver test tools"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI = " \
    file://bitmaskros.h \
    file://fftest.c \
    "

S = "${WORKDIR}"

do_compile() {
	${CC} fftest.c ${LDFLAGS} -o fftest
}

do_install() {
	install -d ${D}${bindir}
	install -m 0755 fftest ${D}${bindir}
}

FILES_${PN} += "${bindir}"
