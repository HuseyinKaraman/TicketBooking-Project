<!-- ABOUT THE PROJECT -->
## TicketBooking Project

<h4>PROJE DIAGRAM </h4>
<a href="#"><img src="img/architectural_approach.png"/></a>
<br/> <br/>

## Proje Konusu:
Online uçak ve otobüs bileti satışı yapılmak istenmektedir. Uygulamanın gereksinimleri 
aşağıdaki gibidir. 


## Gereksinimler;
• Kullanıcılar sisteme kayıt ve login olabilmelidir.  <br/>
• Kullanıcı kayıt işleminden sonra mail gönderilmelidir.  <br/>
• Kullanıcı şifresi istediğiniz bir hashing algoritmasıyla database kaydedilmelidir. <br/>
• Admin kullanıcı yeni sefer ekleyebilir, iptal edebilir, toplam bilet satışını, bu satıştan <br/>
elde edilen toplam ücreti görebilir. <br/>
• Kullanıcılar şehir bilgisi, taşıt türü(uçak & otobüs) veya tarih bilgisi ile tüm seferleri 
arayabilmelidir. <br/>
• Bireysel kullanıcı aynı sefer için en fazla 5 bilet alabilir. <br/>
• Bireysel kullanıcı tek bir siparişte en fazla 2 erkek yolcu için bilet alabilir. <br/>
• Kurumsal kullanıcı aynı sefer için en fazla 20 bilet alabilir. <br/>
• Satın alma işlemi başarılı ise işlem tamamlanmalı ve asenkron olarak bilet detayları 
kullanıcının telefona numarasına sms gönderilmeli. <br/>
• SMS, mail ve push Notification gönderme işlemleri için sadece Database kayıt etme 
işlemi yapılması yeterlidir. Fakat bu işlemler tek bir Servis(uygulama) üzerinden ve 
polimorfik davranış ile yapılmalıdır.  <br/>
• Kullancılar aldığı biletleri görebilmelidir.  <br/>

## Sistem Kabulleri; <br/>
1.Kullanıcılar bireysel ve kurumsal olabilir.
2.SMS, Mail ve Push Notiﬁcation gönderim işlemleri Asenkron olmalıdır.<br/>
3.Uçak yolcu kapasitesi: 189<br/>
4.Otobüs yolcu kapasitesi: 45<br/>
5.Ödeme şekli sadece Kredi kartı ve Havale / EFT olabilir.<br/>
6.Ödeme Servisi işlemleri Senkron olmalıdır.<br/>


<!-- TECHNOLOGIES -->
### Kullanılan Teknolojiler <br/> <br/>
<a href="https://www.java.com" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg" alt="java" width="50" height="50"/> </a> &nbsp;
<a href="https://spring.io/" target="_blank" rel="noreferrer"> <img src="https://www.vectorlogo.zone/logos/springio/springio-icon.svg" alt="spring" width="50" height="50"/> </a> &nbsp;
 <a href="https://www.mongodb.com/" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/mongodb/mongodb-original-wordmark.svg" alt="mongodb" width="50" height="50"/> </a> &nbsp;
 <a href="https://redis.io" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/redis/redis-original-wordmark.svg" alt="redis" width="50" height="50"/> </a> &nbsp;
 <a href="https://www.postgresql.org" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/postgresql/postgresql-original-wordmark.svg" alt="postgresql" width="50" height="50"/></a> &nbsp;
 <a href="https://www.rabbitmq.com" target="_blank" rel="noreferrer"> <img src="https://www.vectorlogo.zone/logos/rabbitmq/rabbitmq-icon.svg" alt="rabbitMQ" width="50" height="50"/></a> &nbsp;
   <a href="https://www.docker.com" target="_blank" rel="noreferrer"> <img src="https://www.svgrepo.com/show/303231/docker-logo.svg" alt="docker" width="50" height="50"/> </a>&nbsp;
 <a href="https://www.junit.org" target="_blank" rel="noreferrer"> <img src="https://user-images.githubusercontent.com/10291265/215849774-9dcf84a2-78bc-450b-b363-312f7ada018e.png" alt="jnuit" width="100" height="50"/></a> &nbsp;


<!-- Postman Query-->
### Postman Üzerinden Atılan İsteklere Örnekler; <br/> <br/>
  
<h4>Create User </h4>
<img src="img/create indivual user.png"  alt="create user"/>
<h4>Get All User </h4>
<img src="img/get users.png"  alt="get user"/>
<h4>Login</h4>
<img src="img/login.png"  alt="login"/>
<h4>User is Logged In</h4>
<img src="img/isLoggedIn.png"  alt="loggedIn"/>
<h4>Is logged In when user not logged in (throw Exception)</h4>
<img src="img/isLoggedIn_when_user_not_login.png"  alt="not login user"/>
<h4>Create Trip with Admin </h4>
<img src="img/create trip admin user.png"  alt="create trip with admin"/>
<h4>Create Trip with Indivual User </h4>
<img src="img/create_trip_with_indivual_user.png"  alt="create trip with indivual user"/>
<h4>Update Trip Status </h4>
<img src="img/update status trip.png"  alt="create trip with admin"/>
<h4>Buy Ticket for 3 Male Passenger with Indivual User</h4>
<img src="img/take ticket to 3 male person.png"  alt="loggedIn"/>
<h4>Buy Ticket for 2 Male Passenger with Indivual User</h4>
<img src="img/buy 2 ticket with indivual user.png"  alt="loggedIn"/>
<h4>Buy Ticket for 22 Passenger with Corparete User</h4>
<img src="img/corparete user buy 22 ticket and excep.png"  alt="login"/>
<h4>Buy Ticket for 20 Passenger with Corparete User</h4>
<img src="img/corparete user buy 20 ticket.png"  alt="login"/>
<h4>Search Trip by transportType , from an to city</h4>
<img src="img/search trip 1.png"  alt="login"/>
<h4>Search Trip by date</h4>
<img src="img/search trip by date.png"  alt="login"/>
<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE.txt` for more information.

<!-- CONTACT -->
## Contact

### Hüseyin Karaman

<a href="https://github.com/" target="_blank">
<img  src=https://img.shields.io/badge/github-%2324292e.svg?&style=for-the-badge&logo=github&logoColor=white alt=github style="margin-bottom: 20px;" />
</a>
<a href = "mailto: @outlook.com?subject = Feedback&body = Message">
<img src=https://img.shields.io/badge/send-email-email?&style=for-the-badge&logo=microsoftoutlook&color=CD5C5C alt=microsoftoutlook style="margin-bottom: 20px; margin-left:20px" />
</a>
<a href="https://linkedin.com/in/" target="_blank">
<img src=https://img.shields.io/badge/linkedin-%231E77B5.svg?&style=for-the-badge&logo=linkedin&logoColor=white alt=linkedin style="margin-bottom: 20px; margin-left:20px" />
</a>  
  
<!-- PROJECT-BOOTCAMP-PRACTICUM PART -->
<br />

## Java Bootcamp - Kodluyoruz & Solmaz
<div align="center">
  <a href="https://www.solmaz.com">
    <img src="img/logos/solmaz-logo.jpg" alt="Logo" width="220" height="200">
  </a>

<h3 align="center">Company: Solmaz Customs Consultancy/Brokerage Co.</h3>
</div>

<div align="center">
  <a href="https://kodluyoruz.org/tr/kodluyoruz/">
    <img src="img/logos/kodluyoruz-logo.png" alt="Logo" width="350" height="300">
  </a>
<h3 align="center">Organizer: Kodluyoruz.org</h3>
</div>

