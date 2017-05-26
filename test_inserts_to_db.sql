use mydb;

INSERT INTO login (email, pwd)
values('makisdelaportas@gmail.com', 'makarosThek1llAh');

insert into organiser(Login_email, Police_id, Name, Surname, Birthdate)
values('makisdelaportas@gmail.com', 'ICECUBE', 'Makis', 'Delaportas', '2017-05-06');
 
insert into eventsgroup(id_events_group, Name, Type, organiser_login_email,description)
values(15, 'Fat Kids Camp', 'Sport', 'makisdelaportas@gmail.com',"Prevent your kid from getting even fatter, join him to the Fat Kids Camp! We do not take the responsibility if an another fat kid, eats your child." );
 
insert into locationowner(login_email, name, surname)
values('makisdelaportas@gmail.com', 'Makis', 'Delaportas');
 
insert into location(id, address, postcode, name, locationowner_login_email)
values(3, 'McDonalds & KFC 420, Galatsi', '12453', 'Fix', 'makisdelaportas@gmail.com');
 
insert into events(id_events, day, time, location_id, organiser_login_email, eventsgroup_id_events_group,price)
values(1, 'Sunday', '16:20', 3, 'makisdelaportas@gmail.com', 15, 42.0);

insert into eventsfeedback(events_id_events, id_events_feedback,content)
values(1,1,"My kid stopped hiding in the toilet to eat food. Unfortunately, now he tries to be a vegan. I am going to give him for adoption. :)");
