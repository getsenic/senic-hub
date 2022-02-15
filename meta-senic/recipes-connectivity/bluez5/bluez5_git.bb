require recipes-connectivity/bluez5/bluez5.inc

REQUIRED_DISTRO_FEATURES = "bluez5"

# [Alan] We want to skip all the backported BlueZ patches that
# came with this Yocto release.
# However, some of the patches are Yocto specific changes that
# need to be applied for BlueZ to build
SRC_URI = " \
        git://git.kernel.org/pub/scm/bluetooth/bluez.git;protocol=https \
        file://out-of-tree.patch \
        file://init \
        file://bluetooth.service \
        file://bluetooth.conf \
"

PV = "5.49+git${SRCPV}"
SRCREV = "e758ebb6a144769777471727e1935620988d22f5"
S = "${WORKDIR}/git"

RDEPENDS_${PN} = "systemd"

# noinst programs in Makefile.tools that are conditional on READLINE
# support
NOINST_TOOLS_READLINE ?= " \
    ${@bb.utils.contains('PACKAGECONFIG', 'deprecated', 'attrib/gatttool', '', d)} \
    tools/obex-client-tool \
    tools/obex-server-tool \
    tools/bluetooth-player \
    tools/obexctl \
    tools/btmgmt \
"

# noinst programs in Makefile.tools that are conditional on TESTING
# support
NOINST_TOOLS_TESTING ?= " \
    emulator/btvirt \
    emulator/b1ee \
    emulator/hfp \
    peripheral/btsensor \
    tools/3dsp \
    tools/mgmt-tester \
    tools/gap-tester \
    tools/l2cap-tester \
    tools/sco-tester \
    tools/smp-tester \
    tools/hci-tester \
    tools/rfcomm-tester \
    tools/bnep-tester \
    tools/userchan-tester \
"

# noinst programs in Makefile.tools that are conditional on TOOLS
# support
NOINST_TOOLS_BT ?= " \
    tools/bdaddr \
    tools/avinfo \
    tools/avtest \
    tools/scotest \
    tools/amptest \
    tools/hwdb \
    tools/hcieventmask \
    tools/hcisecfilter \
    tools/btinfo \
    tools/btsnoop \
    tools/btproxy \
    tools/btiotest \
    tools/bneptest \
    tools/mcaptest \
    tools/cltest \
    tools/oobtest \
    tools/advtest \
    tools/seq2bseq \
    tools/nokfw \
    tools/create-image \
    tools/eddystone \
    tools/ibeacon \
    tools/btgatt-client \
    tools/btgatt-server \
    tools/test-runner \
    tools/check-selftest \
    tools/gatt-service \
    profiles/iap/iapd \
"

do_install_append() {
    install -m 644 ${WORKDIR}/bluetooth.conf ${D}${sysconfdir}/dbus-1/system.d/bluetooth.conf
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/bluetooth.service ${D}${systemd_unitdir}/system
}
