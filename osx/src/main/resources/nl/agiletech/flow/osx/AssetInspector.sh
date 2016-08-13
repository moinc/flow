#!/bin/bash

manufacturer=$(ioreg -c IOPlatformExpertDevice -d 2 | awk -F\" '/manufacturer/{print $(NF-1)}')
echo "\"manufacturer\":\"$manufacturer\","

productName=$(ioreg -c IOPlatformExpertDevice -d 2 | awk -F\" '/product-name/{print $(NF-1)}')
echo "\"productName\":\"$productName\","

boardId=$(ioreg -c IOPlatformExpertDevice -d 2 | awk -F\" '/board-id/{print $(NF-1)}')
echo "\"boardId\":\"$boardId\","

serialNumber=$(ioreg -c IOPlatformExpertDevice -d 2 | awk -F\" '/IOPlatformSerialNumber/{print $(NF-1)}')
echo "\"boardSerial\":\"$serialNumber\""