IMAGE_INSTALL += " \
	qtcanvas3d \
	haptic-feedback \
	python3-psutil \
	python3-urwid \
	python3-s-tui \
	rootfs-resize \
"

IMAGE_INSTALL += " \
	git \
	autoconf \
	autoconf-archive \
	m4 \
	pkgconfig \
	libtool \
	packagegroup-core-buildessential \
"

inherit extrausers

EXTRA_USERS_PARAMS += " \
        usermod -s /bin/bash root   ; \
"
