package org.prezdev.remotetorch.util

import java.net.NetworkInterface
// https://stackoverflow.com/questions/6064510/how-to-get-ip-address-of-the-device-from-code
class IP{
    fun getLanIp() : String{
        val networkInterfaces = NetworkInterface.getNetworkInterfaces()

        for (net in networkInterfaces){
            val interfaceAddresses = net.interfaceAddresses

            for (addr in interfaceAddresses)
                if(!addr.address.isLoopbackAddress)
                    if(!addr.address.hostAddress.contains(":"))
                        println(addr.address)

        }

        return "192.168.0.1"
    }
}