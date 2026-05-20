# See https://git.yoctoproject.org/poky/tree/meta/files/common-licenses
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

# TODO: Set this  with the path to your assignments rep.  Use ssh protocol and see lecture notes
# about how to setup ssh-agent for passwordless access
SRC_URI = "git://git@github.com/cu-ecen-aeld/assignments-3-and-later-CiprianTiro.git;protocol=ssh;branch=main;nobranch=1;NO_SSH_KEYCHECK=1 \
           file://S99aesdsocket"

PV = "1.0+git${SRCPV}"
# TODO: set to reference a specific commit hash in your assignment repo
SRCREV = "59ac16cd6fd9d907fb6c11b65a3522abb5058967"

# This sets your staging directory based on WORKDIR, where WORKDIR is defined at 
# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-WORKDIR
# We reference the "server" directory here to build from the "server" directory
# in your assignments repo
S = "${WORKDIR}/git/server"

inherit update-rc.d

#DEFINE SYSTEM V CONFIGURATION VARIABLES
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "S99aesdsocket"
INITSCRIPT_PARAMS:${PN} = "defaults 99"

# TODO: Add the aesdsocket application and any other files you need to install
# See https://git.yoctoproject.org/poky/plain/meta/conf/bitbake.conf?h=kirkstone
FILES:${PN} += "${bindir}/aesdsocket"
FILES:${PN} += "${sysconfdir}/init.d/S99aesdsocket"

TARGET_LDFLAGS += "-pthread -lrt"

do_configure () {
	:
}

do_compile () {
	oe_runmake
}

do_install () {
	# Install the aesdsocket binary into /usr/bin/
	install -d ${D}${bindir}
	install -m 0755 ${S}/aesdsocket ${D}${bindir}/

	# Install init script into /etc/init.d/ so it runs on startup
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/S99aesdsocket ${D}${sysconfdir}/init.d/
}
