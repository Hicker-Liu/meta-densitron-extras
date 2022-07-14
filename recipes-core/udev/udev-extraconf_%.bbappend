FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append = " \
        file://20-libinput-ignore-ilitek-tp-mouse.rules \
        file://20-drm-fb1-auto-show-logo.rules  \
"

do_install_prepend () {
       install -d ${D}${sysconfdir}/udev/rules.d
       install -m 0644 ${WORKDIR}/20-libinput-ignore-ilitek-tp-mouse.rules ${D}${sysconfdir}/udev/rules.d
       install -m 0644 ${WORKDIR}/20-drm-fb1-auto-show-logo.rules ${D}${sysconfdir}/udev/rules.d
}
