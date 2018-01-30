# nxos-telemetry-parser
Demonstrate NXOS Telemetry parser, using GPB and JSON

There seems to be offset for every UDP packet coming from the Cisco NXOS devices.

This simple project is to demonstrate offset issue.

The data was obtained through a GNS3 lab running a virtual Nexus 9000, with the following configuration:

```
telemetry
  destination-group 100
    ip address 192.168.205.253 port 50001 protocol UDP encoding JSON 
    ip address 192.168.205.253 port 50002 protocol UDP encoding GPB 
  sensor-group 200
    data-source NX-API
    path "show system resources" depth 0
  subscription 600
    dst-grp 100
    snsr-grp 200 sample-interval 30000
```

The management interface on the virtual switch has been configured with the IP address of 1982.168.205.254.

192.168.205.253 is a VM running Linux where `tcpdump` was executed to capture JSON and GPB data.

From the packet capture, the binary payload was extracted and saved on the `src/test/resources` directory.

On both cases, the first 6 bytes should be removed from the payload prior parsing; otherwise an error is obtained.

It is required to know the purpose and the content of these 6 bytes.

To run the tests:

```
mvn test
```