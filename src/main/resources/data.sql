INSERT INTO users(id, email, facebook_id, role) VALUES
    (1, 'example1@gmail.com', 100054896725641, 0),
    (2, 'example2@gmail.com', 100016726783875, 1),
    (3, 'example3@gmail.com', 100064126743752, 0);

INSERT INTO candidates(id, user_id) VALUES
    (1, 1), (2, 3);

INSERT INTO candidate_slots(id, date_from, date_to, candidate_id) VALUES
    (1, CURRENT_DATE + INTERVAL '2 days 9 hours',
     CURRENT_DATE + INTERVAL '2 days 13 hours', 1),
    (2, CURRENT_DATE + INTERVAL '3 days 10 hours',
     CURRENT_DATE + INTERVAL '3 days 17 hours', 2);

INSERT INTO interviewers(id, booking_limit, user_id) VALUES
    (1, 5, 2);

INSERT INTO interviewer_slots(id, day_of_week, t_from, t_to, week_num, interviewer_id) VALUES
    (1, 4, '9:00', '18:00', 202243, 1);
