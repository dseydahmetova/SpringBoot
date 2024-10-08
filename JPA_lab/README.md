Spring Boot JPA Labs
Intro
The purpose of this lab is gain experience with the JpaRepository interface.

Setup
In this lab, you will be continuing with your work from the previous Spring Boot JDBC lab.

Add the following dependency to your existing dependencies:
implementation("org.springframework.boot:spring-boot-starter-data-jpa")
Entities
In the 
entities
 package, create a 
Trade
 class:
annotated with 
@Entity
implements 
Serializable
contains a private enum 
Side
 with values BUY and 
SELL
Long id field annotated with @Id and 
@GeneratedValue(strategy = GenerationType.IDENTITY)
String 
ticker
 field
Double 
price
 field
Integer 
volume
 field
Side 
side
 field
a default no-arg empty constructor
a constructor that sets all fields except id
getters and setters for fields including id
overrides of 
toString()
, 
hashCode()
 and 
equals()
 methods.
In the 
entities
 package, create a 
Porfolio
 class that:

is annotated with 
@Entity
implements 
Serializable
has a Long id field annotated with @Id and 
@GeneratedValue(strategy = GenerationType.IDENTITY)
has a String 
name
 field
has a
List<Trade>
 field, with the following annotations:
@OneToMany(cascade=CascadeType.ALL)
@JoinColumn(name="portfolio_id")
private List<Trade> trades = new ArrayList<>();
When this is done, create:

a default no-arg empty constructor
a constructor that sets the 
name
 field
getters and setters for fields including id
overrides of 
toString()
, 
hashCode()
 and 
equals()
 methods.
JpaRepository
In the 
repos
 package, create a new interface called 
JpaPortfolioRepository

Annotate this interface with 
@Repository
.

Make this interface extend 
JpaRepository<Portfolio, Long>
.

The body of the interface can be empty. A default implementation will be auto-configured and provided. You do not have to write one.

Service
In the 
services
 package create a 
PortfolioService
 interface. Give this interface methods to:
Get all portfolios
Create a portfolio
Get a specific portfolio
Create a class 
RealPortfolioService
 that implements 
PortfolioService
 - annotate it with 
@Service
 and 
@Transactional
.

Inject the 
PortfolioRepository
 with either field or constructor injection.

Implement the required methods in 
RealPortfolioService
 using the 
PortfolioRepository
.

Controller
In the 
controllers
 package create a 
PortfolioController
 class annotated with 
@RestController
, 
@RequestMapping("/portfolios")
 and 
@CrossOrigin
.

Inject the 
PorfolioService
 with either field or constructor injection.

Create endpoints to get and post data from the database via the injected service.

Run the application and use the OpenApi Documentation endpoint to test the functionality. See the previous labs for more detailed instructions.

Testing
Write a test class for your 
PortfolioRepo
 annotaed with 
@DataJpaTest

Inject both a 
TestEntityManager
 and a 
PortfolioRepo
 using 
@Autowired
.

Write your tests.