# Kernel development flow

The biggest issue identified issue in kernel and device tree development 
is the long and error prone turnover in making changes and testing that they
did something.

These are some flow suggestions that might help with that.


## Device tree

#### Building the device tree/ confirming it's loaded/ ssh

```
bitbake linux -c devshell

vi arch/arm/boot/dts/as-is-sun8i-h3-senic-hub.dts
# Edit what you need. Modify model = "something-unique";
make as-is-sun8i-h3-senic-hub.dtb

# Building in the devshell builds in the different dir from the source
# It will be different than the one below and will be printed when you run make as-is-sun8i-h3-senic-hub.dtb
pushd /home/alan/workspace/senic-os/build/tmp-glibc/work/senic_hub-senic-linux-gnueabi/linux/4.14+git+AUTOINC+b240cc6bc2-r0/build

# Confirm the compiled device tree is the one you need
grep "something-unique" arch/arm/boot/dts/as-is-sun8i-h3-senic-hub.dtb

# root@192.168.1.120 this is Hub with Wifi
scp arch/arm/boot/dts/as-is-sun8i-h3-senic-hub.dtb root@192.168.1.120:/boot/devicetree-zImage-sun8i-h3-senic-hub.dtb

# Confirm at runtime you have the correct device tree
# This should be also "something-unique"
cat /sys/firmware/devicetree/base/model
```


#### Building the device tree/ confirming it's loaded/ sdcard copy

```
bitbake linux -c devshell

vi arch/arm/boot/dts/as-is-sun8i-h3-senic-hub.dts
# Edit what you need. Modify model = "something-unique";
make as-is-sun8i-h3-senic-hub.dtb

# Building in the devshell builds in the different dir from the source
# It will be different than the one below and will be printed when you run make as-is-sun8i-h3-senic-hub.dtb
pushd /home/alan/workspace/senic-os/build/tmp-glibc/work/senic_hub-senic-linux-gnueabi/linux/4.14+git+AUTOINC+b240cc6bc2-r0/build

# Confirm the compiled device tree is the one you need
grep "something-unique" arch/arm/boot/dts/as-is-sun8i-h3-senic-hub.dtb

# Insert the usb SDcard and let it automount
sudo cp arch/arm/boot/dts/as-is-sun8i-h3-senic-hub.dtb /media/alan/primary1/boot/sun8i-h3-senic-hub.dtb
# Wait till it blinks twice
sudo cp arch/arm/boot/dts/as-is-sun8i-h3-senic-hub.dtb /media/alan/secondary1/boot/sun8i-h3-senic-hub.dtb
# Wait till it blinks twice
umount /dev/sdb*


# Confirm at runtime you have the correct device tree
# This should be also "something-unique"
cat /sys/firmware/devicetree/base/model
```


#### Getting the active device tree from hub runtime

```
bitbake linux -c devshell
make scripts
scp -r root@192.168.1.120:/proc/device-tree .
./scripts/dtc/dtx_diff device-tree-from-runtime
```

#### Expanding the device tree

If this gives build errors, do the `Building with defconfig` first.

```
bitbake linux -c devshell
make scripts

scripts/dtc/dtx_diff arch/arm/boot/dts/sun8i-h3-senic-hub.dts > dxt_dif_sun8i-h3-senic-hub.dts
```

## Kernel (zImage)

#### Building with defconfig:

```
bitbake linux -c devshell
cp ../../../../../meta-senic/recipes-kernel/linux/files/defconfig arch/arm/configs/senic-defconfig
make defconfig KBUILD_DEFCONFIG=senic-defconfig
```

#### Confirm .config at runtime

Need to enable `CONFIG_IKCONFIG` and `CONFIG_IKCONFIG_PROC`
 in configuration.

This makes the config available from `/proc/config.gz`

```
zcat /proc/config.gz
```

#### Device tree and kernel used by the Hub

The kernel and device tree in the boot partition seems to never be used. Deleting them has no effect on the system. The Hub uses the kernel and devtree from `/boot` on the `primary` and `secondary` partitions. 

#### Appending to kernel version


```
make menuconfig
Search for LOCALVERSION
```

You can check this at runtine with `uname -ra`
