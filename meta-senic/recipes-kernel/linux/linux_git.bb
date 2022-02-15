SECTION = "kernel"
DESCRIPTION = "Mainline Linux kernel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"
COMPATIBLE_MACHINE = "(senic-hub)"

inherit kernel

require linux.inc

# Default is to use stable kernel version
# If you want to use latest git version set to "1"
DEFAULT_PREFERENCE = "-1" 

KERNEL_EXTRA_ARGS += "LOADADDR=${UBOOT_ENTRYPOINT}"
	
# 4.14
PV = "4.14+git+${SRCPV}"
SRCREV_pn-${PN} = "b240cc6bc293322015c160f263ddf06904534d0b"

SRC_URI = "git://github.com/friendlyarm/linux;protocol=https;branch=sunxi-4.14.y \
	file://defconfig \
	file://ble_autoconnect_timeout.patch \
	file://0001-Bluetooth-Fix-connection-if-directed-advertising-and.patch \
	file://0001-Bluetooth-Fix-missing-encryption-refresh-on-Security.patch \
	file://0007-ARM-dts-sun8i-h3-Add-senic-hub-dts.patch \
	"

S = "${WORKDIR}/git"
