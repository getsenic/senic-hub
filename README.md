# Senic Nuimo Hub (discontinued)

This repo contains some information to enable a developer to repurpose the Senic Hub hardware if they so choose.

### Hardware
- [NanoPi Neo](http://www.nanopi.org/NanoPi-NEO_Feature.html) (NanoPi-Neo_V1.31)
- D52M (D52MD2M8IA)
- Generic WiFi dongle

### Hardware enablement
The `meta-senic` directory contains yocto recipes to build a working Linux kernel and bluez stack for the Senic Hub hardware.

Note: The Senic Hub used `u-boot` with some patches applied to enable `mender` support ([see here](https://github.com/getsenic/u-boot/tree/senic/v2017.09)). However, you can use the currently supported `sun8i-h3-nanopi-neo.dtb` which will work fine.