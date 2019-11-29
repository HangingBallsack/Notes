# <font color = red>STRIDE and SQL Injection - 04</font>
| S-T-R-I-D-E             | Description |
| :------------------     | ----        |
|Spoofing                 | Transmissions with intentially mislabeled source|
|Tampering                | Modification of persistent data or data in transport|
|Repudiation              | Denial of having performed unauthorized operations, in systems where these operations cannot be traced |
|Information disclosure   | Access to data in an unathorized fasion |
|Denial of Service        | Rendering a service unaccessible to intended users|
|Elevation of priviledge  | Non-priviledged users gaining access to priviledged operations and data|

## **Trusting trust**
- To trust a program after reading the source code *we must trust the compiler to compile correctly*
- To trust the compiler we can read the source code, but without trusting the compiler we cannot trust the resulting executable
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
- For two programs, X and Y, are **functionally equivalent** if the output of X is the same as the output of Y when they are given the same input. But for example bubble sort and quicksort isn't the same.

**Conclusions**
- Should a "trusting trust" type attack be part of our threat model?
- Thompson argues that at some point one must trust the people behind the software
- Wheeler's diverse double-compiling strategy givesd guarantees under som assumptions

## **Vulnerabilities**
*A **Vulerability** is a weakness in the computational logic found in software and some hardware components that, when exploited, results in a negative impact to confidentiality, integrity, OR availabiliy*

### Disclosure
There is a spectrum of different stances:
**No disclosure:** No details should be made public  
**Coordinated disclosure:** Details can be disclosed after fixes made and embargo lifted  
**Full-disclosure:** Full details should be publicly disclosed, and arguing against an embargo

### CVE
*Common Vulnaribilities and Exposures* is a database of software vulnerabilities. Maintained by **The Mitre Corporations** in the USA.

The list has entries consisting of:
- A *unique number* (CVE-YYYY-XXXX) identifying the vulnerability  
- A description  
- At least one public reference

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
<img src = img/CVSS.png width = 600></img>

### CWE - Common Weakness Enumeration
A category system for software weaknesses and vulnerabilities.

Example: 
- Category 121 is for stack-based buffer overflows

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
- **Static analysis:** inspects `source code`
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
- **Dynamic analysis**: inspects the `running software`
  - *Fuzzer:* Feeds random data to the program to trigger anomalies.
  - *Crawlers:* Maps out the attack surface of the program (spider).
  - *Man-in-the-middle proxy:* Analysis data from normal usage.
  - *Vulnerability scans:*
    - SQL injection tests
    - XSS tests
    - Anti CSRF token detection
    - ...
  - will be able to tell you that your program crashes when given invalid UTF-8 strings (for example)

# <font color = red>Access Control - 06</font>
## **Mandatory vs discretionary**
In a *Mandatory Access Control (MAC)* system, the access control policies are fixed by a central authority
in a *Discretionary Access Control (DAC)* system, a user who has access to an object can specify permissions for it or transfer access to another actor.

### Mandatory Access Control
Modern operating systems have MAC (read; mandatory access control) on resources such as CPU, memory and storage.   
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

    <img src = img/ACM.png width = 600></img>

**Example:**
In Unix-like systems:
- Subjects: Processes
- Objects: Files, sockets, processes

Permissions are structured according to users and groups
- User ID **(UID)**  
- Group ID **(GUID)**  
- Special UID: 0 **(root)**. Can ignore mot permission restrictions

Every file has:
- Owner ID
- Owner GID

### Role based access control (RBAC)
In a role based access control system, a set of roles abstract the permissions from users


<img src = img/RBAC.png width = 600></img>

**Example:**

- U (users): {alice, bob}  
- R (roles): {doctor, patient}  
- P (permissions): {writePerscription, withdrawMedicine}  
- RolePerm: {(doctor, writePerscription), (patient, withdrawMedicine)}  
- UserRoles: {(alice, doctor), (bob, patient), (alice, patient)}

### Capability based access control
Gidder ikke skrive mer om dette, kanskje senere :P :P :P 

# <font color = red>OS and application security - 07</font>
## **Operating systems**
What is the role of the operating system?
- Orchestrate processes (software).
- Provide an abstract interface for hardware (drivers).

Programs can communicate with the OS through **system calls,** which interrupts the program and returns the control to the OS

**OS level priviledge separation**   
On the OS level, individual processes have different protection for different resources:
- Memory:
   - Virtual memory mapping
   - Limits
- CPU
   - Scheduling priority
- File system
   - Permissions
   - chroot or other visibility restrictions
   - quotas
- Open files/sockets/network connections
   - file descriptors
   - limits

## **Memory protection**
**Virtual memory mapping**

- Each program gets their own virtual address space
- Memory location not decided at compile time
- Memory fragmentation hidden from programs
- Easy to page out to swap (store memoty on hard drive)  

But, as a consequence: Processes cannot directly address or access the memory of other processes

Exceptions:
1. Processes can allocate shared memory
2. A process can attach themselves as a debugger to another process  
   - Number 2 is allowed by default in Linux for processes with the same UID 

## **File system abstraction**
### The UNIX file system  
The *unix* file system provides a unified way to access file systems based in the **root directory /**

Directories group the files into logical parts:
- `/bin`:     programs
- `/sbin`:    administrative programs
- `/etc`:     system configuration
- `/dev`:     virtual file system of devices
- `/home`:    individul user's home folders
- `/tmp`:     temporary files
- ...

### Chroot
The operating system can restrict process file access by **changing the root dir to a different directory**
- **Example:** After chroot /home/bar the path /bin/foo translates to /home/bar/bin/foo
- **Note:** a UID=0 (root) process easily access resources outside the new root,
this provides a form of file system virtualisation

Usefulness of chroot is limited by the restriction that only root can do it.
- Imagine that a user could set up a root folder with a forged /etc/passwd and /etc/shadow
- Then they could fool a SUID program (such as su) to give them UID=0 shell

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
## **Preventing privilege escalation**
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
- Bugs in unprivileged part will ideally only result in denial of service for the misbehaving client
- More intense scrutiny can be given to privileged parts
- Simplifying the privileged part makes reasoning about its security easier

## **Implementing the monitor/slave pattern**
Privileged operations:
- File access
- accessing cryptographic keys
- database access
- spawning pseudo-terminals
- binding to a network interface

Monitor does actons on the slaves behalf

# <font color=red>Authentication - 09</font>
## **Passwords**
### Guidelines
**The guidelines for passwords are:**
- Requre a minimum password length
- The minimm length requirements must be 8 characters or greater
- Allow at least 64 characters
- check against a list of known bad passwords. For instance:
   - Dictionary words
   - Repetitive or sequential characters (aaaaa123, 1234abcd)
   - Context-specific words, such as for Facebook, the password "Facebook123"
   - Passwords obtained form previous breach corpuses   


**Structure of user authentication scheme based on passwords:**
1. Provide a way for user to authenticate server (HTTPS)
2. Establish a secure communication channel (HTTPS)
3. User transmits password
4. Server verifies password:
   1. Salted (128 bits)
   2. Run through an expensive key derivation function (ex. SCrypt)
5. Server respons with a secure session ID
6. Client program stores session ID as securely as possible


### Storing passwords
> foobar→aec070645fe...  
> foobat→c7f0f45765b...

Requirements of a cryptographic hash function:
- **One way:** Given y, difficult to find x such that h(x) = y
- **Collision free:** Difficult to find x and x' such that h(x) = h(x')
- A small change in input yield a large difference in output
- quick to compute  
  
*Bad (has collisions):*
- MD5
- SHA1  

*Good (no collisions):*  
- SHA256/512
- SHA3

#### Rainbow tables
Rainbow tables refer to a time-space-tradeoff when creating a **lookup table for hash values -> plaintext**

#### Salting
Efficient solution to make rainbow tables/ hash dictionaries infeasible instead of storing `h(x)`, generate random byte-string s and store `s, h(h(x)⊕s)`

**How much salt?**   
UNIX-like systems use 128-bits salts.  
Salting does not help against a brute-force attack on a single password


## **Two-factor authentication**
- SMS codes (Considered insecure: Reddit developers hacked via SMS intercept)
- Print-out with one-time codes
- A device with time-based, one-time passwords (TOTP)
- Approval from an already authenticated device (Example: Keybase)
- Public key cryptography (U2F /FIDO, WebAuthn(new W3C standard))

## **Public key cryptography and authentication**
1. Generate key-pair
2. Exchange public keys
3. Compute shared sevret

Public keys must be authenticated, many different schemes for this:
- Web-of-trust (key-signing)
- Trust upon first use
- Centralised certificate authorities
- ...


### Man-in-the-middle attacks

<img src = img/MiM.png width = 600></img>

Assumption: The man in the middle does not strike the first time  
Mechanism: Trust the public key used in first session. Use that for  authentication of later session

Works well for long-lasting trust-relationships. Or when no existing trust relationship exists (i.e web-site registration)

### Centralized Certificate Authorities
Assumption: We trust a central authority to verify public keys for us  
Mechanism: Central authority verifies identity and issues certificates on public keys  
Examples:
  - Browsers ship with a list of public keys of trusted Certificate Athorities
  - Organisations can distribute their own certificates for internal use

### Logged in
How do we ensure that each request comes from a valid logged in user?

Example:
  - /login
      - User request login form, and enters password
  - /inbox
      - User posts login details to the inbox page
      - Server responds with inbox, listing messages, after checking password
  - /delete?messageid=123
      - User requests a message deleted
     - **How can the server know the user is the same?**  

The standard way solution is to use **session ID**, which identifies the user in the following session.  

Requires:
- Entropy: Session ID must not be guessable (random, 128 bits)
- Secrecy: Session ID must not be leaked:
  - HTTPS
  - Debugging modes often leak session IDs
  - Cross-Site-Scripting (Cookies: HttpOnly, SameSite)

### Structure of a user authentication scheme based on passwords
1. Provide a way for user to authenticate server (HTTPS w/valid certificate)
2. Establish a secure communication channel (HTTPS)
3. User transmits password
4. Server verifies password:
   - Salted (128 bits)
   - Run through an expensive key derivation function (SCrypt)
5. Server responds with a secure session ID
6. Client program stores session ID as securely as possible

# <font color = red> Web security: TLS and HTTPS - 10</font>
## **The World Wide Web**
**Communication on the www:**
- Domain Name Service (DNS)
- Hyper Text Transfer Protocol (HTTP)
- Uniform Resource Identifier (URI)

**Web-servers respond to HTTP requests**
- Static websites vs dynamic web sites
- Dynamic: Any language can be used on the server side

### HTTP requests
- **GET** is the most common request type. it fetches a resource at a specified URI
- **HEAD** fetches only the headers for the specified resource
- **POST** Posts content to a specified resource.

Each request contains headers which specify meta-data about the request:
- Accepted formats/languages
- Cookies
- User agent
- ETag
- ...

### HTTP responses
The server reponds with:
- a status message (200, 404, 500 ...)
- headers
- (posibly) the content of requested resource

### HTTP Strict Transport Security
Is a HTTP reponse header, which tells the client to **always use HTTPS with this domain.**   
HSTS can be preloaded into browsers

Protects agains:
- User accepting a bad certificate
- Downgrade to plaintext HTTP
- Old HTTP bookmarks   
**Note:** if your domain is on the preload list, you cannot change back to HTTP - clients will no longer accept it


## **Stream ciphers and Message Authentication Codes**
### Stream ciphers
Most modern cryptography is based on block ciphers.
- Fixed input and output length (e.g 128 bits)
- Deterministic: Same key and input gives same output   
**Problem:** Most applications have variable length input/output   

    <img src=img/StreamCipher.png width=450></img>

- Stream ciphers are based on cryptographic pseudo-random number generators (CPRNGs)
- provides safe extension to arbitrary inputs   
BUT: They are malleable!

#### TLS
- Previous versions include weak ciphers ( < version 1.3)
- Provides:
  - Confidentiality
  - Authentication (Via X.509 certificates)
  - Forward secrecy

  If you need cryptographic transport security: use TLS 1.3

TLS version 1.3 has reduced the number of supported ciphers:
- AES in counter mode and CBC-MAC
- ChaCha20 and Poly1305 MAC   

#### HTTPS
HTTP can be transmitted over TLS(HTTPS). Authentication provided by Certificate Authorities (such as Let's Encrypt):
- Same-origin protocol separates HTTP from HTTPS **(i.e HTTP =/= HTTPS)**
- Many sites still serve content over plaintext HTTP

# <font color = red>Cross site scripting - 11</font>
## Same-origin policy
An **origin** is a triple:
- Protocol
- Domain
- Port number   

Example: https://uib.no/ gives:
- Protocol: https
- Hostname: www.uib.no
- Port number: 443   

The **same-origin policy** restricts scripts run in the browser to only *access resources from the same origin*.   
Example: A script can only access cookies from the same origin   
- The following URLs have the same origin:
  - http://geocities.com/bob/index.html
  - http://geocities.com/eve/script.html   

## Cross-site scripting
Web browser insulate resources, such as cookies or JavaScript, from different origins.   
*Cross-site scripting* (XSS) occurs when a web-server unintentionally serves JavaScript from an attacker to client browsers.   
This allows attacker code to access resources from victim server origin   

Example:   
```JavaScript
$username = $_GET['username'];
echo '<div class="header"> Welcome, ' . $username . '</div>';
```   
Now username could contain JavaScript which can:
- Steal session cookie
- Trick the user to give their password by showing fake login screen
- Mine bitcoins
- ...


How does the attacker inject scripts?
- User data from one user visible to another (Example: The Samy worm on MySpace)
- URL variables
- User data from post requests
- Evaluating user data in client side script


### XML HttpRequest
Scripts can make HTTP requests to the current origin.   
This means that once an attacker has injected a script, he can do anything the user could do:
- GET pages
- POST forms
- ...   
**Example:** The Samy Worm used POST requests to update the profile, and add the user samy as a friend

## XSS prevention strategies
### Filtering input
- Blacklisting is bad security practice.
- The disallowed characters (<>(){}[] * " ') are quite common
- Client side checking is easy to circumvent

### Escaping output
For a string placed inside an HTML element (`<div>DATA</div>`), we can do the following:
- & -> \&amp;
- < -> \&lt;
- \> -> \&gt;
- " -> \&quot;
- ' -> \&#x27
- / -> \&#x2F   

### The DON'Ts
Avoid inserting untrusted data in tag names   
`<NEVER PUT UNTRUSTED DATA HERE... href="/test" />`

Avoid inserting untrusted data in attribute names   
`<div ... NEVER PUT UNTRUSTED DATA HERE ... =test />`

Avoid inserting utrusted data in scripts   
`<script> ... NEVER PUT UNTRUSTED DATA HERE ... </script>`

Avoid inserting untrusted data directly in CSS   
``` CSS
<style>
... NEVER PUT UNTRUSTED DATA HERE ...
</style>
```

Avoid stupid shit like dis:   
- `{ background-url : "javascript:alert(1)"; }`
- `{ text-size: "expression(alert("XSS"))"; }`


### The DOs
- HTML sanitisers (Example OWASP AntiSamy project)
- Using another markup language (Markdown BBCode) with safe conversion to HTML
  - Markdown allows literal HTML, which must be sanitized
  - Many BBCode implementaton do nothing to prevent XSS   
- Notice: Even graphical formatting tools must represent the formatting in some waym abd can be just as vulnerable to XSS as code-based ones

# <font color=red>CWE-352: Cross-Site Request Forgery (CSRF) - 12</font>
## Securing the session token
Cookies are **not covered** by same origin policy by default:
- Cookies from **https:**//example.com/ wil be sent to **http:**//example.com

Solution: Set the Secure flag on the cookie to `True`

### The SameSite flag
The SameSite flag has three possible values:

| Flag        | Description               |
|:-------     | :-------                  | 
|**none**     | The cookie is always sent |
|**strict**   | The cookie is only sent the request is initiated from the same origin
|**lax**      | The cookie is still sent when followin links (GET requests) from other origins, but not with other requests (POST, DELETE, ...)

Browser support for this flag is improving, but CSRF tokens are still recommended

### The HttpOnly flag
In 2002, the most pupular way to exploit XSS was stealing the session token using JavaScript.   
The HttpOnly flag for cookies indicates to browsers that the cookie:
- should only be sent in the HTTP-header
- should not be available to scripts


### Cookie conclusion
The following three flags should be set:
- Secure
- SameSite (lax or strict depending on use case)
- HttpOnly (is not really effective)

But: If your site already uses a lot of JavaScript, consider keeping the session token in local storage.

## Cross site request forgery protection
What?
- Links must be proteted https://site/action#abs6ajv...
- Forms must be protected
- All other POST/GET requests (thorugh XMLHttpRequests)

**Pitfall:** Using double submit tokens

Keeping the CSRF-token stored on the server is annoying. It is tempting to put them in a cookie:
- Cookie:
  - Csrf-Token=.......
- Form-field:
  - `<input type="hidden name="token">......</input>`

But, this means that if the attacker can set a cookie for the domain, he can forge requests:
- Subdomains can set cookies for the whole domain
- HTTP can set (but not read 'Secure') cookies for HTTPS

**CSP - Content Security Policy**

Policies set in the HTTP header:
- Control which sources content are allowed to come from
- Violations are reported back to the server

## Recap - Web security
- Same-origin policy
- Cross-site scripting:
  - Escaping (different contexts)
  - Sanitizing HTML (use a good library)
  - CSP
- Cross-site request forgery
  - Add **anti-CSRF token** on any form
  - Make sure that any authority-bearing token-cookies (such as session cookies) have the SameSite **flag** set to strict (or lax if GET requests do not have **any** side effects)
- Cookie flags

# <font color=red>Capability based security - 13</font>
a **capability** (known in some systems as a **key**) is a communicable, unforgeable token of authority.

A process/object/user/service/... should only have as much privilege as needed to perform their intended task.

A *capability* consists of:
- A reference to an object
- A set of permissinos for that object

A capability is used **whenever a resource is accessed.**

**Example:** read(capability):   
- Reads from the object pointed to by capability
  - If capability allows reading

## Using capabilities
Restricting access to programs:
- Give only the capabilities needed
- What capabilities should be given to:
  - a word processor?
  - a web site?
  - a system login manager?

This allows very fine grained applicatinos of the principle of least privilege

### Unforgeable
If a capability can be forged, it is useless as a security measure.   
Two approaches to unforgeability:
- Enforced by supervisor (operating systems, virtual machine, compiler, ...)
  - In an OS, the kernel can keep a **table of capabilities** for each process
    - A capability is **just an index** in the table.
    - Since the process cannot access its table, it cannot forge capabilities
  - Example:
    - File descriptors on UNIX
- Unguessable capabilities (random tokens, cryptographic signatures, ...)
  - Relies on entropy and cryptographic security to prevent forging
    - A capability can be referenced by a random number
    - A capability can be signed
  - must be used when transferring capabilities over networks

### Memory safe capabilities
A memory safe **object capability system** can be obtained by:
- **Endowment**: Alice might have intrinsic capabilities given to her at her creation
- **creation**: Alice gets capability to access an object she creates
- **introduction**: Alice transfers a capability to Bob

This approach **relies on the memory safety** of the language.

Example:   
- Bank account capabilities:
  - Deposit D
  - Withdraw W
  - Read balance R
- Attenuation:
  - Alice wants Bob to transfer her some money
  - Alice has a (D, W, R) capability to her own account
  - Alice creates a new (D) capability to her account and transfers it to Bob

The creater of a capability should be able to revoke it.

CSRF-tokens can be viewed as capabilities:
- Denotes an object (form target) and permission (POST, GET, ...)
- Unforgeable (unguessable)

### Capabilities summary
A capability consists of:
- A **reference** to an object
- A set of **permissions** for that object

A Capability is a unforgeable, transferrable token of authority

**Not related to capability based security:**
- POSIX capabilities
- Docker capabilities

# <font color=red>Incorrect deserialisation - 14</font>
## Capsicum
**Privilege separation**   
Drawbacks:
- Chroot requires UID 0
- When transitioning between privileges, data must be serialised
- Relies on shared memory
- Reasoning about security requires modelling monitor as a state machine
- Does not limit network access rom slave

**Capsicum**   
Design:
- Introduces a special **capability mode** for processes
- Provide **new kernel primitives** (`cap_enter`, `cap_new`, ...)
- Changes existing kernel primitives when in capability mode
- **Userspace library** (libcapsicum).   

    <img src=img/Capsicum.png width = 600></img>

- In capsicum, **capabilities are file descriptors** along with a set of acess rights
  - there are about 60 possible access rights for a capability in capsicum
- A capability is created throguh `cap_new` by giving it a file descriptor and rights mask
  - Capabilities are transferred through Inter Process Communication (IPC) channels, such as sockets.

.
.
.
.
.
.

## Insecure deserialisation   
**Serialization** is the process of turning objects of a programming language into byte arrays for transport.   
- Java serialization
- JSON (Multiple language support)
- Pickle (Python)
- Protocol buffers
**Deserialization** is the process of turning these byte arrays back into objects.

  The code doing deserialization is at the forefront of the program security.   
Bugs in deserialization can often lead to *remote code execution*.

# <font color=red>Security through the software development cycle - 15</font>
**Software security** is the ability of software to function according to intentions in an **adverserial enviroment**

*ASSUMPTIONS -> SECURITY MECHANISMS -> SECURITY REQUIREMENTS*

1. **Identify security requirements** which capture the intentions for the software.
2. **Make explicit the assumptions** about the enviroment the software will run.
3. **Design mechanisms** which satisfy the requirements given the assumptions

## Non-functional requirements

### Availability
**Availability** is the proportion of time a system spends in a functional state.

Causes for downtime:
- Malicious attacks
- Software bugs
- Hardware failure
- Failure of services
- Exessive usage (exhaustion of scarse resources: CPU/GPU etc)

### Capacity
**Capacity** refers to the maximum number of simultaneous users/transactions.

### Scalability
**Scalability** is the ability to increase capacity

What are the bottle-necks?

Running multiple instances:
- Load balancing (DNS round-robin)
- Location
- Secure communication between instances
- Eventual consistency

### Performance
**Performance** is:
- Responsiveness of the software to users
- Rate of transaction processing

Covers both **latency and throughput**

### Efficiency
**Efficiency** is the ability to make use of scarse resources such as:
- Memory / Cache
- Processing power
- Storage
- Network bandwidth
- Latency

Increasing software efficiency gives a better performance/hardware requirement

### Portability
**Portability** is the ability of the software to run on different systems with little adaptation
- Language dependent (Assembly vs C vs Java)
- Portability favours abstractions
- Documentation

## Recoverability
**Recoverability** is the *time to recover from distruptive events*.

### Cohesion
**Cohesion** is the degree to which parts of a system / module belong together.

Strong cohesion: Each module is **robust** and **reusable**   
Contrast with **coupling**, the interdependency wetween modules

## Threat model
- What **threats** can an attack pose (STRIDE)?
- Which part of the system is likely to be **controlled by an attacker**?
- what **motivates** an attacker?
- What **attack vectors** can an attacker use?

## Logging
What to log:
- Authentication events
- Attempted intrusions
- Violations of invariants
- Unusual behaviour
- Performance statistics

What not to log:
- Sensitive information
- Keys
- Passwords
- User data

## Monitoring
In order to resond to an ongoing threat four things must happen:
1. Detection
2. Logging
3. Monitoring
4. Response

## Securing development and deployment
Security is important during development:
- An attacker who can modify the source code can make his own back-doors
- How can we trust third pary libraries and APIs?

# <font color = red>Language based security - 16</font>
An object is *immutable* if it cannot be changed after creation.   
Example: String is an immutable class in Java.

Why are strings immutable in Java?
- Because of string interning:
  - Every copy of a string is stored only once
- Allows memorization of hashcodes (for say HashMap):
  - Since the string doesn't change, we never have to recompute the hashcode
- Thread safety
- Security

If you pass reference to a mutable object, you give permission to mutate the object.   
If you receive a reference to a mutable object, you must accept that it mutates beyond your control.

- Passing a reference only gives "read access"
- When receiving a reference you can safely test for invariants
- Thread safety for free

**Never Date in Java**   
The Date class i mostly deprecated and should never be used.   
Use `java.time.Instant` - which is better *(and immutable)*

The **final** keyword for variables mean:
- The reference cannot be changed after initialisation
- Any constructor must initialise the field
- Declaring class final is **not** enough to make it safe

## The `null` reference
`null` can mean:
- No element was found (maps)
- No parameter was present (getting parameters from HTTP requests)
- This is a left value, when right is null in Either

  Easy to forget null checks

### `NullPointerExceptions`
Oftens leads to unexpected control flow:
- When the exceptions is "`throws`", execution races back up the stack.
- Can be caught by `catch (Exception ...)` clauses
- ... or crash the thread / program

# <font color=red>Privacy: GDPR - 18</font>
## Privacy, a human right?
**Information privacy** refers to the ability of the individual to control their personal information

Personal information is any information attachable to a specific (physical) person and includes:
- Name and ID number
- Birthdate and gender
- Residence and locatoin
- Healthcare records
- Political information
- Criminal records
- ...


**Threats to privacy:**
- Collection of information
  - Heatlth-care and other public sevices (example: NAV)
  - School records
  - Credit-card usage
  - Traveling
  - Surveillance cameras
  - Internet browsing
- Aggregation of information
  - Combining different data bases (Example: Credit rating)
  - De-anonymisation
  - Security breaches
  - Using data for a different purpose
  - https://panopticlick.eff.org/ 
- Dissemination of information
  - Selling personal data to advertisers/ credit lenders/ employers
  - Exposing emotionally important or taboo information about a subject (life experiences, nudity, private relationships ...)
  - Exposing someone's personal information to encourage harrassment (doxing)

    ... or threatening to do so (blackmail)

## Current law
- EU directive:
  - General Data Protection Regulation (GDPR)
    - specifies:
      - the **rights** of individuals
      - the **obligations** of data processors
    - Lawfulness
      - Processing shall be lawful only if and to the extent that at least on of the following applies:
        - give consent
        - necessary for a contract
        - necessary for legal obligation
        - necessary to protect vital interests
        - necessary for public interest
        - necessary for the purpose of legitimate interests
    - Fairness
      - Controllers are obliged to protect the fundamental rights of the data subject
      - Automated decisions should be predictable
    - Transparency
      - Information about what is collected must be clearly stated where the data is collected
- Norwegian law:
  - Personopplysningsloven av 2018 incorporates GDPR into Norwegian law
  - Datatilsynet is the Norwegian supervisory authority on privacy issues

## GDPR: Rights of the individual
- Right of access (art. 15)
- Right to rectification (art. 16)
- Right to erasure (art. 17)
- Right to data restriction
- Right to data portability (art. 20)
- Right to object

## Data breach
In the case of a personal data breach, the controller shall (...)
- not later than 72 hours after having become aware of it (...)
- notify the personal data breach to the supervisory authority

## Tracking
- Cookies and web-storage
- HTML5 canvas finger printing
- "Like"-buttons (even without pressing it)
- Web-beacons
- Analytics software
- Advertisement
- Cross-device tracking (ultra-sonic tracking)

## Anonymity vs Privacy
The following are different:
- Privacy (control over private information)
- Anonymity (absense of identification)
- Pseudonymity

# <font color=red>Mobile security- 20</font>
## Sandboxing:
Android processes are separated using usual Linux mechanisms:
- SELinux provides **Mandatory Access Control** (from v5.0)
  - Each application runs in its own SELinux sandbox (from v9.0)
- seccomp is used to **filter system calls* (from v8.0)


## Encrypted storage
Android (since v5.0) uses dm-crypt to encrypt the phone storage
- dm-crypt is a whole disk encryption utility

Provides **confidentiality** but **not integrity**

## iOS
Widely believed to be the most secure mobile OS:
- developed in-house from hardware to native applications
- Heavy investment in security
