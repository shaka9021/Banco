# LIBERTY SERVER XML

```xml
<server description="server for bank applications">

    <!-- Enable features -->

    <featureManager>
        <feature>javaee-7.0</feature>
        <feature>localConnector-1.0</feature>
    </featureManager>

    <!-- To access this server from a remote client add a host attribute 
        to the following element, e.g. host="*" -->

    <httpEndpoint httpPort="9080" httpsPort="9443" id="defaultHttpEndpoint"/>

    <!-- define queue -->

    <messagingEngine>
        <queue forceReliability="ReliablePersistent" id="employeeq" maxMessageDepth="5000">
        </queue>
    </messagingEngine>

    <!-- define conection manager -->

    <connectionManager id="employeeconmgr01" maxPoolSize="2"/>

    <jmsQueueConnectionFactory connectionManagerRef="employeeconmgr01" jndiName="jms/employeecf">
        <properties.wasJms nonPersistentMapping="ExpressNonPersistent" persistentMapping="ReliablePersistent"/>
    </jmsQueueConnectionFactory>

    <!-- define a session to the queue -->

    <jmsQueue id="jms/employeesession" jndiName="jms/employeesession">
        <properties.wasJms deliveryMode="Application" priority="1" queueName="employeeq" readAhead="AsConnection" timeToLive="500000"/>
    </jmsQueue>

    <jmsActivationSpec id="bank-service/EmployeeMessageListener">
        <properties.wasJms destinationRef="jms/employeesession"/>
    </jmsActivationSpec>

    <!-- Automatically expand WAR files and EAR files -->

    <applicationManager autoExpand="true"/>

    <keyStore id="defaultKeyStore" password="CHANGE IT"/>

    <!-- mysql jdbc driver -->

    <library id="mysql">
        <fileset dir="${shared.resource.dir}/mysql" includes="*.jar"/>
    </library>

    <!-- declare the runtime database -->
    <dataSource id="jdbc-bank" jndiName="jdbc/bank">
        <jdbcDriver libraryRef="mysql"/>
        <properties databaseName="bank" password="CHANGE IT" portNumber="3306" serverName="localhost" user="bank"/>
    </dataSource>

    <!-- declare user registry -->
    <basicRegistry id="basic-registry">

        <user name="scorpio" password="CHANGE IT"/>
        <user name="simpson" password="CHANGE IT"/>

        <group name="EMPLOYEE">
            <member name="scorpio"/>
            <member name="simpson"/>
        </group>

    </basicRegistry>

    <applicationMonitor updateTrigger="mbean"/>
</server>
``` 