# <font color = red>STRIDE and SQL Injection - 04</font>
- **Spoofing**
  - Transmissions with intentially mislabeled source
- **Tampering**
  - Midification of persistent data or data in transport
- **Repudiation**
  - Denial of having performed unauthorized operations, in systems where these operations cannot be traced
- **Information disclosure**
  - Acces to daa in an unathorized fasion
- **Denial of Service**
  - Rendering a service unaccessible to intended users
- **Elevation of priviledge**
  - Non-priviledged users gaining access to priviledged operations and data

## **Trusting trust**
- To trust a program after reading the source code *we must trust the compiler to compile correctly*
- To trust the compiler we can read the source code, but without trusting the compiler we cannt trust the resulting executable
- *Conclusion:* To trust the compiler we must trust the compiler, which is circular

### Compiler bootstrapping
```C 
c = next();
if (c != '\\')
    return c;
c = next();
if (c == '\\')
    return '\\';
if (c == 'n')
    return '\n';
```
- A Compiler could try to recognise that it is compiling the login command of the OS: 
```C 
if(match("pattern of login")) {
     compile("backdoor");
     }
```
- ... and then compile in a back door.


### Diverse double compiling
- For two programs, X and Y, are **functionally equivalent** if the output of X is the same as the output of Y when they are given the same input

**Conclusions**
- Should a "trusting trust" type attack be part of our threat model?
- Thompson argues that at some point one must trust the people behind the software
- Wheeler's diverse double-compiling strategy givesd guarantees under som assumptions

## **Vulnerabilities**
>*A **Vulerability** is a weakness in the computational logic found in software and some hardware components that, when exploited, results in a negative impact to confidentiality, integrity, OR availabiliy*

### Disclosure
There is a spectrum of different stances:
>**No disclosure:** No details should be made public  
>**Coordinated disclosure:** Details can be disclosed after fixes made and embargo lifted  
>**Full-disclosure:** Full details should be publicly disclosed, and arguing against an embargo

### CVE
*Common Vulnaribilities and Exposures* is a database of software vulnerabilities. Maintained by **The Mitre Corporations** in the USA
The list has entries consisting of:
> A *unique number* (CVE-YYYY-XXXX) identifying the vulnerability  
> A description  
> At least one public reference

#### CVE number assignment
- Assigning the CVE numbers is taken care of by the CVE Numbering Authorities (CNAs), which each have different scopes. These include:
  - The Mitre corporations (Primary CNA)
  - Distributed Weakness Filing Project (For open-source projets)
  - Many corporations (Google, Microsoft, Intel, Netflix ...)

#### What is CVE used for?
- Easier than referencing product/version/description
- Easy to track vulnerability fixes
- Provides a quick way to look up vulnerabilities

#### CVSS - Common Vulnerability Scoring System - assigning a score to a vulnerability
- Base metrics
- Temporal metrics
- Enviromental metrics

**CVSS Example:**   
<img src=CVSS.png width = 600></img>


#### NVD - National Vulnerability Database
The national vulnerablity Database contains analysis of known vulnerabilities:
- CVE numbers
- CWE numbers
- CVSS
- Versions affected

Software vedors often have separate security advisories, some examples:
- Microsoft Security Bulletin
- Apple Secuirty Advisory (APPLE-SA)
- Debian Security Advisory (DSA)
It is good practise to **subscribe to advisories** of the vendors of your platform

### Security Tools
- **Static analysis:** inspects ```source code```
  - *Program flow analysis*
  - *Constraint analysis*
  - *Logic tests*
  - *Linting*
  - will be able to tell you that this code is illogical:

``` Java
boolean checked = false;
if(checked){
    //...
}
```
- **Dynamic analysis**: inspecs the ```running software```
  - *Fuzzer:* feeds random data to the program to trigger anomalies
  - *Crawlers:* Maps out the attack surface of the program
  - *Man-in-the-middle proxy:* analysis data from normal usage
  - *Vulnerability scans:*
    - SQL injection tests
    - XSS tests
    - Anti CSRF token detection
    - ...
  - will be able to tell you that your program crashes whne given invalid UTF-8 strings (for example)

# <font color = red>Access Control - 06</font>
## **Mandatory vs discretionary**
In a *Mandatory Access Control (MAC)* system, the access control policies are fixed by a central authority
in a *Discretionary Access Control (DAC)* system, a user who has access to an object can specify permissions for it or gtransfer access to another actor.

### Mandatory Access Control
Modern operating systems have MAC(read; mandatory access control) on resources such as CPU, memory and storage.
In addition there are systems for introducing more MAC based security:
- SELinux
- Linux Security Modules (AppArmor)
- Mandatory Integrity Control on Windows (Extending ACLs)
- Language based mechanisms (e.g. Java Security Manager)

### Discretionary access control
- File systems
- E-mail
- WiFi passwords
- ...

## **Access Control Models**
### Access control lists
In a system with access control lists, permissions are assigned to objects:
- Each object has a list of permissions assgned to different users.
Typical (but not always), the access control list specifies an owner of the object

<img src = ACM.png width = 600></img>

**Example:**
In Unix-like systems:
- Subjects: processes
- Objects: files, sockets, processes

Permissions are structured according to users and groups
- User ID **(UID)**
- Group ID **(GUID)**
Special UID: 0 **(root)**. Can ignore mot permission restrictions

Every file has:
- Owner ID
- Owner GID

### Role based access control (RBAC)
In a role based access control system, a set of roles abstract the permissions from users


<img src = RBAC.png width = 450></img>

**Example:**

>U (users): {alice, bob}  
>R (roles): {doctor, patient}  
>P (permissions): {writePerscription, withdrawMedicine}  
>RolePerm: {(doctor, writePerscription), (patient, withdrawMedicine)}  
>UserRoles: {(alice, doctor), (bob, patient), (alice, patient)}

### Capability based access control
Gidder ikke skrive mer om dette, kanskje senere :P :P :P 

# <font color = red>OS and application security - 07</font>
## **Operating systems**
What is the role of the operating system?
- Orchestrate processes (software)
- Priovide an abstract interface for hardware (drivers)

Programs can communicate with the OS through **system calls,** which interrupts the program and returns the control to the OS

**OS level priviledge separation**
On the OS level individual processes have different protection for different resources:
>- Memory:
>   - Virtual memory mapping
>   - Limits
>- CPU
>   - Scheduling priority
>- File system
>   - Permissions
>   - chroot or other visibility restrictions
>   - quotas
>- Open files/sockets/network connections
>   - file descriptors
>   - limits

## **Memory protection**
**Virtual memory mapping**

>- Each program gets their own virtual address space
>- memory location not decided at compile time
>- memory fragmentation hidden from programs
>- easy to page out to swap (store memoty on hard drive)  
>
> But, as a consequence: processes cannot directly address or access the memory of other processes

Exceptions:
>1. Processes can allocate shared memory
>2. A process can attach themselces as a debugger to another process  
>- Number 2 is allowed by default in Linux for processes with the same UID 

## **File system abstraction**
### The UNIX file system
>The *unix* file system provides a unified way to access file systems based in **the root directory /**

Directories group the files into logical parts:
>- /bin: programs
>- /sbin: administrative programs
>- /etc: system configuration
>- /dev: virtual file system of devices
>- /home: individul user's home folders
>- /tmp: temporary files
>- ...

### Chroot
The operating system can restrict process file access by **changing the root dir to a different directory**
>- **Example:** After chroot /home/bar the path /bin/foo translates to /home/bar/bin/foo
>- **Note:** a UID=0 (root) process easily access resources outside the new root,
This provides a form of file system virtualisation

Usefulness of chroot is limited by the restriction that only root can do it.
>- Imagine that a user could set up a root folder with a forged /etc/passwd and /etc/shadow
>- Then they could fool a SUID program (such as su) to give them  UID=0 shel

## **OS virtualisation**
**Docker:** 
Docker is an open source OS-virtualisation program which runs software packages called **containers**
- containers are systematically seperated using OS mechanisms
- Containers are templated by *images*
- Container construction and administration through the daemon dockerd

- Based on Linux' OS-level separation mehanisms
  - Chroot
  - namespaces which gives each container:
    - individual mount tables
    - individual process tabkes
    - individual network stack
    - individual UID tables

**Docker security:**
Docker security can be decomposed into:
- Security of the underlying OS level separation mechanisms
- The dockered daemon attack surface
- Security of the container configuration

***A CONTAINER IS NOT A VM***

# <font color = red>Privilege separation - 08</font>
## **Preventing privilede escalation**
Typical sevice behaviour:
- Accept requests from network (untrusted)
- Authenticate user
- Allow priviledge operation to authenticated users

**Problem:** Difficult to safely escalate priviledges once the user is authenticated

Monitor:
- Priviledged
- Provides an interface for slave to perform privildged operations
- Validates the requests to perform operations
- Finite state machine
Slave:
- Unprivileged
- Does most of the work
- Calls on monitor when privileged operations must be performed

**Basic principle:** Limit the amout of code running in a privileged process


**Benefits:**
- Without further holes in the monitor, RCE vulnerabilites are confined to the slave
- Bugs in unprivileged part will ideally only result in denial of service for the mesbehaving client
- More intense scrutiny can be given to privileged parts
- Simplifying the privileged part makes reasong about its security easier

## **Implementing the monitor/slave pattern**
Priveleged operations:
- File access
- accessing cryptographic keys
- database access
- spawning pseudo-terminals
- binding to a network interface

Monitor does actons on the slaves behalf

# <font color=red>Authentication - 09</font>
## **Passwords**
### Guidelines
The guidelines for passwords are:
>- Requre a minimum password length
>- The minimm length requirements myst be 8 characters or greater
>- Allow at least 64 characters
>- check against a list of known bad passwords. For instance:
>   - Dictionary words
>   - Repetitive or sequential characters (aaaaa123, 1234abcd)
>   - Context-specific words, such as for Facebook the password "Facebook123"
>   - Passwords obtained form previous breach corpuses
  
### Storing passwords
> foobar→aec070645fe...  
> foobat→c7f0f45765b...

Requirements of a cryptographic hash function:
>- **One way:** Gien y, difficult to find x such that h(x) = y
>- **Collision free:** Difficult to find x and x' such that h(x) = h(x')
>- A small change in input yield a large difference in output
>- quick to compute  
  
*Bad (has collisions):*
>- MD5
>- SHA1  

*Good (no collisions):*  
>- SHA256/512
>- SHA3

#### Rainbow tables
> Rainbow tables refer to a time-space-tradeoff when creating a **lookup table for hash values -> plaintext**

#### Salting
> Efficient solution to make rainbow tables/ hash dictionaries infeasible instead of storing ```h(x)```, generate random byte-string s and store ```s, h(h(x)⊕s)```

**How much salt?**
> UNIX-like systems use 128-bits salts.  
> Salting does not help against a brute-force attack on a single password

## **Two-factor authentication**
- SMS codes (Considered insecure: Example Reddit developers hacked via SMS intercept)
- Print-out with one-time codes
- A device with time-based, one-time passwords (TOTP)
- Approval from an already authenticated device (Example: Keybase)
- Public key cryptography (U2F /FIDO, WebAuthn(new W3C standard))

## **Public key cryptography and authentication**
1. Generate key-pair
2. Exchange public keys
3. Compute shared sevret

### Man-in-the-middle attacks

<img src=MiM.png width = 450></img>

> Assumption: The man in the middle does not strike the first time  
> Mechanism: Trust the public key used in first session. Use that for  authentication of later session
> 
> Works well for long-lasting trust-relationships. Or when no existing trust relationship exists (i.e web-site registration)

### Centralized Certificate Authorities
> Assumption: We trust a central authority to verify public keys for us  
> Mechanism: Central authority verifies identity and issues certificates on public keys  
> Examples:
>   - Browsers ship with a list of public keys of trusted Certificate Athorities
>   - Organisations can disgtribute their own certificates for internal use

### Logged in
How do we ensure that each request comes from a valid logged in user?

Example:
>   - /login
>       - User request login form, and enters password
>   - /inbox
>       - User posts login details to the inbox page
>       - Server responds with inbox, listing messages, after checking password
>   - /delete?messageid=123
>       - User requests a message deleted
>      - **How can the server know the user is the same?**  

The standard way solution is to use **session ID**, which identifies the suer in the following session.  
Requires:  
> - Entropy: Session ID must not be guessable (random, 128 bits)
> - Secrecy: Session ID must not be leaked:
>    - HTTPS
>   - Debugging modes often leak session IDs
>   - Cross-Site-Scripting (Cookies: HttpOnly, SameSite)

### Structure of a user authentication scheme based on passwords
> 1. Provide a way for user to authenticate server (HTTPS w/valid certificate)
> 2. Establish a secure communication channel (HTTPS)
> 3. User transmits password
> 4. Server verifies password:
>    - Salted (128 bits)
>    - Run through an expensive key derivation function (SCrypt)
> 5. Server responds with a secure session ID
> 6. Client program stores session ID as securely as possible

# <font color = red> Web security: TLS and HTTPS - 10<font>
