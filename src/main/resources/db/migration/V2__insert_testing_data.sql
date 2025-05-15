-- Crear las salas de cine
INSERT INTO public.rooms (status,"name","number") VALUES
	 (true,'Sala 1',50),
	 (true,'Sala 2',100);

-- Crear butacas
INSERT INTO public.seats (status,"number","row_number",room_id) VALUES
	 (true,3,1,2),
	 (true,4,1,2),
	 (true,1,2,1),
	 (true,2,2,1),
	 (true,3,2,1),
	 (true,4,2,1),
	 (true,1,2,2),
	 (true,2,2,2),
	 (true,3,2,2),
	 (true,4,2,2);
INSERT INTO public.seats (status,"number","row_number",room_id) VALUES
	 (true,1,3,1),
	 (true,2,3,1),
	 (true,3,3,1),
	 (true,4,3,1),
	 (false,1,1,1),
	 (false,2,1,1),
	 (false,3,1,1),
	 (false,1,1,2),
	 (false,2,1,2),
	 (true,4,1,1);

-- Crear las películas
INSERT INTO public.movies (status,allowed_age,genre,length_minutes,"name") VALUES
	 (true,18,'SCIENCE_FICTION',120,'Avengers: End Game'),
	 (true,16,'DRAMA',130,'Titanic'),
	 (true,14,'SCIENCE_FICTION',150,'Avatar'),
	 (true,18,'HORROR',98,'El Silencio de los Inocentes');

-- Crear las carteleras
INSERT INTO public.billboards (status,"date",start_time,end_time) VALUES
	 (true,'2025-05-13 18:37:01.085','2025-05-14 00:00:00','2025-05-14 23:59:00'),
	 (true,'2025-05-13 18:52:01.085','2025-05-15 00:00:00','2025-05-15 23:59:00');

-- Crear las funciones
INSERT INTO public.billboard_movies (status,show_time,billboard_id,movie_id,room_id) VALUES
	 (true,'2025-05-14 11:30:00',1,1,1),
	 (true,'2025-05-14 13:45:00',1,2,1),
	 (true,'2025-05-14 11:30:00',1,4,2),
	 (true,'2025-05-14 13:30:00',1,3,2);

-- Crear clientes
INSERT INTO public.customers (status,age,document_number,email,last_name,"name",phone_number) VALUES
	 (true,28,'1313131313','jacha067@gmail.com','Chiquito','Javier','593999999999'),
	 (true,18,'0965432100','caro@gmail.com','Cárdenas','Ashley','593963852714'),
	 (true,44,'0912345678','correo@gmail.com','Villamar','Yadira','593987654321');

-- Crear reservas
INSERT INTO public.bookings (status,"date",billboard_movie_id,customer_id) VALUES
	 (true,'2025-05-14 12:42:14.510928',1,1),
	 (true,'2025-05-14 15:01:38.244482',3,1),
	 (true,'2025-05-14 15:01:38.244482',3,2);

-- Asignar butacas a reservas
INSERT INTO public.bookings_seats (booking_id,seats_id) VALUES
	 (1,1),
	 (1,2),
	 (1,3),
	 (3,5),
	 (2,6);