INSERT INTO users(id, email, facebook_id, role)
VALUES (1, 'example1@gmail.com', 100054896725641, 0),
       (2, 'example2@gmail.com', 100016726783875, 1),
       (3, 'example3@gmail.com', 100064126743752, 0),
       (4, 'example4@gmail.com', 100064126743753, 1);
alter sequence users_id_seq restart with 5;

INSERT INTO candidates(id, user_id)
VALUES (1, 1),
       (2, 3);
alter sequence candidates_id_seq restart with 3;

INSERT INTO candidate_slots(id, date_from, date_to, candidate_id)
VALUES (1, CURRENT_DATE + INTERVAL '1 days 8 hours',
        CURRENT_DATE + INTERVAL '1 days 14 hours', 1),
       (2, CURRENT_DATE + INTERVAL '3 days 10 hours',
        CURRENT_DATE + INTERVAL '3 days 17 hours', 2);
alter sequence candidate_slots_id_seq restart with 3;

INSERT INTO interviewers(id, booking_limit, user_id)
VALUES (1, 5, 2),
       (2, 3, 4);
alter sequence interviewers_id_seq restart with 3;

INSERT INTO interviewer_slots(id, day_of_week, t_from, t_to, week_num, interviewer_id)

VALUES (1, 5, '9:00', '18:00', 202246, 1),
       (2, 5, '8:00', '19:30', 202246, 2),
       (3, 5, '8:00', '19:30', 202246, 2),
       (4, 4, '10:00', '16:30', 202247, 1);
alter sequence interviewer_slots_id_seq restart with 5;

INSERT INTO bookings(id, description, t_from, status, subject, t_to, candidate_slots_id,
                     interviewer_slots_id)
VALUES (1, 'description', CURRENT_DATE + INTERVAL '2 days 9 hours', 1, 'subject',
        CURRENT_DATE + INTERVAL '2 days 13 hours', 1, 1);
VALUES (2, 'description', CURRENT_DATE + INTERVAL '2 days 9 hours', 1, 'subject',
        CURRENT_DATE + INTERVAL '2 days 13 hours', 1, 2);
VALUES (3, 'description', CURRENT_DATE + INTERVAL '2 days 9 hours', 1, 'subject',
        CURRENT_DATE + INTERVAL '2 days 13 hours', 1, 3);
alter sequence bookings_id_seq restart with 4;
