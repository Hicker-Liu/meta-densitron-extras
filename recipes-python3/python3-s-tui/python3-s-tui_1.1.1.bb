
SUMMARY = "Stress Terminal UI stress test and monitoring tool"
HOMEPAGE = "https://github.com/amanusk/s-tui"
AUTHOR = "Alex Manuskin <amanusk@tuta.io>"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://setup.py;md5=e896310fa992666ae2dc89291c18dc6b"

SRC_URI = "https://files.pythonhosted.org/packages/99/42/3f9b77425911bced84c1c7af44d543984446b03430590d39d553b01ba268/s-tui-1.1.1.tar.gz"
SRC_URI[md5sum] = "4d63bfff0e4ce60907590ff6cf6f8027"
SRC_URI[sha256sum] = "c5d7d5105659705454b7dee870a7abe9d9f277e7b87402c7c0346ce3782a93b2"

S = "${WORKDIR}/s-tui-1.1.1"

RDEPENDS_${PN} = ""

inherit setuptools3
