inherit core-image
CORE_IMAGE_EXTRA_INSTALL += "aesd-assignments"
CORE_IMAGE_EXTRA_INSTALL += "openssh"

# Force the creation of the nc command shortcut linking directly to the busybox engine
IMAGE_LINK_NC() {
    ln -sf /bin/busybox ${IMAGE_ROOTFS}/usr/bin/nc
}
ROOTFS_POSTPROCESS_COMMAND += "IMAGE_LINK_NC; "

inherit extrausers
# See https://docs.yoctoproject.org/singleindex.html#extrausers-bbclass
# We set a default password of root to match our busybox instance setup
# Don't do this in a production image
# PASSWD below is set to the output of
# printf "%q" $(mkpasswd -m sha256crypt root) to hash the "root" password
# string
PASSWD = "\$5\$2WoxjAdaC2\$l4aj6Is.EWkD72Vt.byhM5qRtF9HcCM/5YpbxpmvNB5"
EXTRA_USERS_PARAMS = "usermod -p '${PASSWD}' root;"
