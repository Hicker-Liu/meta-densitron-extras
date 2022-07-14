do_install_append () {
    rm -rf  ${D}/home 
}

FILES_${PN}_remove = "/home"
