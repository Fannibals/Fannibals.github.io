## Oracle Cloud Infrastructure

### Foundation courses' content

- Cloud Concepts
- OCI Architecture
- Networking Services
- Compute Services
- Storage Services
- Database Services
- IAM Service
- Security
- Pricing and Billing



### Lesson 1: Cloud Concepts 

1. **Cloud Computing** 

   **<font color=red>1) On-Demand Self-Service</font>**

   - Provision computing capabilities as needed automatically without requiring human interaction with service provider.

   **<font color=red>2) Broad network access</font>**

   - Capabilities are available over the network and accessed through standard mechanisms.

   **<font color=red>3) Resource pooling</font>** 

   - The provider's computing resources are pooled to serve multiple consumers using a multi-tenant model, with different resources dynamically assigned and reassigned according to demand.

   **<font color=red>4) Rapid elasticity</font>** 

   - Capabilities can be elastically provisioned and released.

   **<font color=red>5) Measured service</font>**

2. **Service Models**

- <img src="/Users/Ethan/Desktop/Fannibals.github.io/Cloud/pic/Screen Shot 2020-05-02 at 5.35.09 pm.png" alt="Screen Shot 2020-05-02 at 5.35.09 pm" style="zoom:80%;" />

3. **High Availability**
   - Systems have redundant Hardware & Software so that makes the system **available despite failure**
   - When the failure occurs, the **failover process moves processing performed by the failed component to the backup component,** which should be transparent to customers.

4. **Disaster Recovery**
   - **Recovery Point Objective**
     - how much data loss or transaction loss can your business tolerate.
   - **Recovery Time Objective**
     - how much downtime your business can tolerate.

5. **Other Terminology**

   - **Fault Tolerance** : how a cloud vendor will ensure minimal downtime for service provided
     - ie. backup load balancers 
   - **Scalability** 
     - scaling out /in : horizontal scaling (+ # of servers)
     - scaling up/down : vertical scaling

   + **Elasticity** : the ability to quickly increase or decrease resources

+ **CAPEX v.s. OPEX**
  + **CAPEX:** cost for buy, maintain fixed assets (building, equipment, computers..) 
  + **OPEX:** cost for running a product, business or system.
  + **Cloud lets you trade CAPEX for OPEX**



### Lesson 2: OCI Architecture

1. **Architecture**
   - **Regions** : Localized gro ares , { >=1 AD}
   - **Availability Domains (AD)** 
     - One or more fault-tolerant, isolated data centres located within a region
     - connected to each other by a low-latency, high bandwidth network.
   - **Fault Domains (FD)**
     - grouping of hardware and infrasturcture within an AD to provide anti-affinity

2. **Choosing a region**
   - Location
   - Data Residency & Compliance
   - Service availability 
3. **Availability Domains**
   - ADs do not share physical infrastructure
4. **Fault Domains** 
   - Logical data centre

5. **High Availability Design**
   - Fault Domains
   - Availability Domains 
   - Region Pair

6. **Compartment** 
   - a collection of related resources. It helps you isolate and control access to your resources.
   - Root compartment can control all resources.
   - six levels deep
   - VM ? Docker?



---



## Core OCI Services

### Lesson 3 OCI Compute Services

-   **Categories**

![Screen Shot 2020-05-03 at 2.49.12 pm](/Users/Ethan/Desktop/Fannibals.github.io/Cloud/pic/Screen Shot 2020-05-03 at 2.49.12 pm.png)

1. **Bare Metal** means Direct Hardware Access
2. **VM :** A **hypervisor(管理程序)** to virtualise the underlying bare metal server into smaller VMs (**multi-tenant VMs**)
   - Using it when you want to control all aspect of an environment. 
3. **DVH:**  Run your VMinstances on dedicated bare metal servers (**single-tenant VMs**) , greater security



**AutoScaling**

- running instance --> config
- config --> instance pool

- scaling rule



**Containers v.s. VMs**

- Hardware -- Hypervisor -- VM (OS - Depen - App)

- Hardware -- OS -- Container(Dep -- App)

  

**How to deploy Containers?**

- Manually **SSH** into machines and run **Docker**
  - **Pro:** Simple and easily understood
  - **Con:** Not automated, no reproducible, doesn't scale/heal
- **Scripting or Config** management tools
  - **Pro:** Integrates with existing env, easily understood
  - **Con:** doesn't scale/heal
- **Orchestration Systems**
  - **Pro:** Automated, reproducible, scale & heal
  - **Con:** overhead, additional tooling and traning required



**Oracle Kubernetes Engine** 

- K8s is an open source system for automating deployment, scaling and management of containerized applications.
- FD { Node { Container} Pod }



**Functions**

- Push container to registry
- Configure function trigger
- Code runs only when triggered
- Pay for code execution time only



### Lesson 4 Oracle Storage services

1. **Block Volume**
   - **Storage for compute instances**
   - Two types:
     - Boot Volume (OS disk)
     - Block Volume (data disks)
   - To store data independently and beyond the lifespan of compute instances
   - Use cases: DB, VMware
   - highly durable (replica, backups)
2. **Local NVMe (Non-Volatile Memory Express)**
   - Temporary NVMe based storage locallly attached to the compute instances.
   - High performance
3. **File Storage**
   -  Hierarchical collection of docs organized into named directories which are themselves structured files.
   - Distr file standards - NFS & SMB
   - Shared file system storage for compute instances.
   - Data-protection: Snapshots
4. **Object Storage**
   - All data is managed as objects
   - Each object is stored in a bucket, which is a logical container for storing objects.
   - Objects are stored in a single, flat struc.
   - Ideal for storing unlimited amount of unstructured data
     - images, media files, logs, backups...
5. **Archive Storage**
   - cheaper than hot (standard storage tier)

![Screen Shot 2020-05-05 at 5.30.37 pm](/Users/Ethan/Desktop/Fannibals.github.io/Cloud/pic/Screen Shot 2020-05-05 at 5.30.37 pm.png)

 

### Lesson 5 OCI Networking Services

1. **Virtual Cloud Network (VCN)**

   - **Software defined private network** that you set up in OCI. 
   - Lives in an OCI region
   - Enables OCI resources such as compute instances to securely communicate with internet, other instances or on-premises(本地) data centres.

   **VCN address space**

   - address space is a range of IP address that you assign to a VCN, E.g. 10.0.0.0/16
     - Range: 10.0.0.0 - 10.0.255.255
   - compute instances are placed in subnets

   **Communication to Internet**

   - Internet gateway provides a path for network traffic bwn your VCN and the internet
   - NAT Gateway enables outbound connections to the internet, but blocks inbound connections initiated from the internet. (Updates, patches)

   **Dynamic Routing Gateway (DRG)**

   - DRG si a virtual router that provides a path for private traffic bwn VCN & dest other than the internet
   - IPsec VPN & FastConnect

   **Using different Gateways to implement different services.**

2. **VCN Security**

   - A common set of **firewall rules** associated with a subnet and applied to all instances launched inside the subnet.
   - Network Security Group consists of set of rules that apply only to a set of VNICs of your choice.

   **Communication to other VCNs: Peering**

   - VCN peering is the process of connecting multiple VCNs. BY using **private IP addresses**
   - **Local VCN Peering** (conn VCNs in the same region)
   - **Remote VCN Peering** (conn VCNs in different regions)

   **Load Balancer**

   - A load balancer sits between the clients and the backends performs tasks such as: 
     - Service Discovery: 
     - Health Check
     - Algorithm
   - **Load Balancer benefits** 
     - Fault tolerance and HA
     - Scale
     - Naming abstractions



### Lesson 6 OCI Identity and Access Management

1. **IAM**

   **Principal:** an IAM entity that is allowed to interact with OCI resources

   **Compartment:** a collection of related resources

   - each resource belongs to a single compartment.
   - polices can be given to manage the access of different group of users.

2. **Authentication**

   - Whos is this person ?
     - User name, pwd
     - API signing key
     - Auth Tokens

3. **Authorization**

   - OCI Authorization = Policies

4. **Policies**



### Lesson 7 OCI Database Services

1. **OCI DB Options**

   - Virtual Machine DB Systems
   - Bare Metal DBSs
   - RAC
   - Exadata DBSs
   - Autonomous - Shared/Dedicated

2. **DB Systems**

   Operations

   - Launch, start, stop or reboot
   - Scale
   - Patch

3. **DB Systems Backup**

4. **DB Systems HA and DR**

   - Oracle Data Guard provides a set of services that create, maintain, manage and monitor one or more standby dbs to enable Oracle dbs to survive disasters and data corruptions. It maintains sync bwn the primary & standby dbs.

5. **Autonomous DBs**

   **Fully managed db with 2 workload types:**

   - Autonomous Transaction Processing
   - Autonomous Data Warehouse

   **Deploymeny options**

   - Dedicated : exclusive use of the Exadata hardware
   - Shared

![Screen Shot 2020-05-05 at 9.02.14 pm](/Users/Ethan/Desktop/Fannibals.github.io/Cloud/pic/Screen Shot 2020-05-05 at 9.02.14 pm.png)