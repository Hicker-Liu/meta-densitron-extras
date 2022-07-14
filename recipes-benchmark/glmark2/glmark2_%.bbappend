FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
	file://0010-native-state-drm-restrict-udev-enumeration-force-to-card1.patch \
"

