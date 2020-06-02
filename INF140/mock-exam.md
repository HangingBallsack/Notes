# Part 3. Text-entry Questions
## Access Controls and Network Security (Section 5)
### Question 1 (5pt).

In a computer system, access is the flow of information between two entities. A/An ***subject*** is an active entity that requests access to a/an ***resource***. A/An ***object*** is a passive entity that contains information or needed functionality.

Access control is a broad term that covers several different types of mechanisms that enforce access control features on computer systems, networks, and information. When a user wants to access a system, progressively, there will be four security controls in the system:
***cryptography***, ***security protocols***, ***firewalls*** and ***access controls***.

An access control mechanism dictates how subjects access objects. There are different access models. A system that uses ***discretionary access control*** enables the owner of the resource to specify which subjects can access specific resources. In ***mandatory access control***, users do not have the discretion of determining who can access objects. Instead, this model greatly reduces the amount of rights, permissions, and functionality a user has for security purposes.

### Question 2 (5pt).
The Transmission Control Protocol/Internet Protocol (TCP/IP) is a suite of protocols that governs the way data travels from one device to another. Different from the Open Systems Interconnection (OSI) model, the TCP/IP model has five layers, which from bottom to top are ***hardware***, ***network access***, ***Internet***, ***transport*** and ***application***.

Cryptography plays a critical role in secure communications. In the
TLS/SSL protocol suit, both RSA and Diffie-Hellman are used for key ex- change between the client and the server. Suppose the client and the server choose Diffie-Hellman for key exchange. The public parameter is set as p = 1013 with generator 2. Assume the client generates a private key Kc = 13 and the server generates a private key Ks = 11. Then the client’s public key is ***2^13(mod1013)*** and the server’s public key is ***2^11(mod1013)***. After they exchange these public keys, they share a secret information: ***2^24(mod1013)***. If the shared secret information, with ASCII encoding, is taken as the input for MD5 to generate a 128-bit encryption key for AES, then the encryption key in hexadecimal form is ***63 34 30 31 35 62 37 66 33 36 38 65 36 62 34 38 37 31 38 30 39 66 34 39 64 65 62 65 30 35 37 39***.

## Part 2
### Question 1
