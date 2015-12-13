Distributed Systems Daniel Maloney G00303381

To Run:
1. Place the cracker.war (located in Files) into the tomcat/webapps directory.
2. Startup tomcat to create the files cracker files in the tomcat webapps folder
3. To start the service navigate to the tomcat/webapps/cracker/WEB-INF/ lib directory.
4. Run the .jar file by using the following command:
	java -cp ./vigenere.jar ie.gmit.sw.VigenereBreakerImpl "./tolstoy.txt"
4.1 The filename is required as a parameter, any file can be used instead of "tolstoy.txt".
5. Open a browser and and type localhost:8080/cracker in the address window to navigate to the service.
6. Enter the keylength in the window, followed by the cypher-code in the windows below.
	Typing: "CQNBDYANVNJACXOFJA" with a key length of 4 will return: "THESUPREMEARTOFWAR".
7. Press the crack cypher button.

GitHub URl: https://github.com/G00303381/Distributed-Systems-Cypher-Breaker.git

Notes:
The RMI service appears to fall over occasonally (I believe something to do with the threads) which may result in the
keys being cycled through however not returning the best score and key.