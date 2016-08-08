#!/bin/bash

# enum interfaces
interfaces=$(networksetup -listallhardwareports | grep 'Device:' | sed "s/Device: //" | tr " " "\n")
# alternative: interfaces=$(ifconfig -l | tr " " "\n")
echo "interfaces:"
for interface in $interfaces
do
    echo "  \"$interface\":"
    
    status=$(ifconfig $interface | grep "status" | tr -d "\t")
    IFS=" " read -r -a statusArray <<< "$status"
    echo "    -\"status\":\"${statusArray[1]}\""
    
    ether=$(ifconfig $interface | grep "ether" | tr -d "\t")
    IFS=" " read -r -a etherArray <<< "$ether"
    echo "    -\"mac\":\"${etherArray[1]}\""
    
    mtu=$(ifconfig $interface | grep "mtu" | tr -d "\t")
    IFS=" " read -r -a mtuArray <<< "$mtu"
    echo "    -\"mtu\":\"${mtuArray[3]}\""
    
    inet=$(ifconfig $interface | grep "inet " | tr -d "\t")
    IFS=" " read -r -a inetArray <<< "$inet"
    echo "    -\"ip4\":\"${inetArray[1]}\""
    echo "    -\"netmask4\":\"${inetArray[3]}\""
    
    inet6=$(ifconfig $interface | grep "inet6" | tr -d "%$interface" | tr -d "\t")
    IFS=" " read -r -a inet6Array <<< "$inet6"
    echo "    -\"ip6\":\"${inet6Array[1]}\""
    echo "    -\"netmask6\":\"${inet6Array[3]}\""
done

# default interface
defaultInterface=$(route -n get 0.0.0.0 2>/dev/null | grep "interface" | sed -e 's/\interface://g' | tr -d " ")
echo "\"defaultInterface\":\"$defaultInterface\""

# hostname
hostname=$(hostname -s)
echo "\"hostname\":\"$hostname\""
fqdn=$(hostname -f)
echo "\"fqdn\":\"$fqdn\""
domain=$(echo "$fqdn" | sed -e "s/$hostname.//g")
echo "\"domain\":\"$domain\""

# dhcp
dhcp=$(ipconfig getpacket "$defaultInterface" | grep "server_identifier" | sed -e 's/\server_identifier (ip)://g' | tr -d " ")
echo "\"dhcp\":\"$dhcp\""

# router
router=$(ipconfig getpacket "$defaultInterface" | grep "router" | sed -e 's/\router (ip_mult)://g' | tr -d " {}")
echo "\"router\":\"$router\""

# dns
dnsservers=$(scutil --dns | grep "nameserver" | sort | uniq | sed -e s/\nameserver.*://g | tr -d " ")
echo "\"dns\":"
for dns in $dnsservers
do
	echo "  -\"$dns\""
done