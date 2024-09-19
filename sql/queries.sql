-- https://pgexercises.com/questions/updates/insert.html
-- "Insert some data into a table"
-- The club is adding a new facility - a spa. We need to add it into the facilities table. Use the following values:
-- facid: 9, Name: 'Spa', membercost: 20, guestcost: 30, initialoutlay: 100000, monthlymaintenance: 800.
INSERT INTO cd.facilities (
        facid,
        name,
        membercost,
        guestcost,
        initialoutlay,
        monthlymaintenance
    )
VALUES (9, 'Spa', 20, 30, 100000, 800);
-- https://pgexercises.com/questions/updates/insert3.html
-- "Insert calculated data into a table"
-- Let's try adding the spa to the facilities table again. This time, though, we want to automatically generate the value for the next facid, rather than specifying it as a constant. Use the following values for everything else:
-- Name: 'Spa', membercost: 20, guestcost: 30, initialoutlay: 100000, monthlymaintenance: 800.
INSERT INTO cd.facilities (
        facid,
        name,
        membercost,
        guestcost,
        initialoutlay,
        monthlymaintenance
    )
VALUES (
        (
            SELECT max(facid)
            FROM cd.facilities
        ) + 1,
        'Spa',
        20,
        30,
        100000,
        800
    );
-- https://pgexercises.com/questions/updates/update.html
-- "Update some existing data"
-- We made a mistake when entering the data for the second tennis court. The initial outlay was 10000 rather than 8000: you need to alter the data to fix the error.
UPDATE cd.facilities
SET initialoutlay = 10000
WHERE facid = 1;
-- https://pgexercises.com/questions/updates/updatecalculated.html
-- "Update a row based on the contents of another row"
-- We want to alter the price of the second tennis court so that it costs 10% more than the first one. Try to do this without using constant values for the prices, so that we can reuse the statement if we want to.
UPDATE cd.facilities facs
SET membercost = tc1.membercost * 1.1,
    guestcost = tc1.guestcost * 1.1
FROM (
        SELECT *
        FROM cd.facilities
        WHERE facid = 0
    ) tc1
WHERE facs.facid = 1;
-- https://pgexercises.com/questions/updates/delete.html
-- "Delete all bookings"
-- As part of a clearout of our database, we want to delete all bookings FROM the cd.bookings table. How can we accomplish this?
DELETE FROM cd.bookings -- https://pgexercises.com/questions/updates/deletewh.html
    -- "Delete a member FROM the cd.members table"
    -- We want to remove member 37, who has never made a booking, FROM our database. How can we achieve that?
DELETE FROM cd.members
WHERE memid = 37;
-- https://pgexercises.com/questions/basic/where2.html
-- "Control which rows are retrieved - part 2"
-- How can you produce a list of facilities that charge a fee to members, and that fee is less than 1/50th of the monthly maintenance cost? Return the facid, facility name, member cost, and monthly maintenance of the facilities in question.
SELECT facid,
    name,
    membercost,
    monthlymaintenance
FROM cd.facilities
WHERE membercost > 0
    AND membercost < monthlymaintenance / 50;
-- https://pgexercises.com/questions/basic/where3.html
-- "Basic string searches"
-- How can you produce a list of all facilities with the word 'Tennis' in their name?
SELECT *
FROM cd.facilities
WHERE name LIKE '%Tennis%';
-- https://pgexercises.com/questions/basic/where4.html
-- "Matching against multiple possible values"
-- How can you retrieve the details of facilities with ID 1 and 5? Try to do it without using the OR operator.
SELECT *
FROM cd.facilities
WHERE facid in (1, 5);
-- https://pgexercises.com/questions/basic/date.html
-- "Working with dates"
-- How can you produce a list of members who joined after the start of September 2012? Return the memid, surname, firstname, and joindate of the members in question.
SELECT memid,
    surname,
    firstname,
    joindate
FROM cd.members
WHERE joindate > '2012-09-01';
-- https://pgexercises.com/questions/basic/union.html
-- "Combining results FROM multiple queries"
-- You, for some reason, want a combined list of all surnames and all facility names. Yes, this is a contrived example :-). Produce that list!
SELECT surname
FROM cd.members
UNION
SELECT name
FROM cd.facilities;
-- https://pgexercises.com/questions/joins/simplejoin.html
-- "Retrieve the start times of members' bookings"
-- How can you produce a list of the start times for bookings by members named 'David Farrell'?
SELECT starttime
FROM cd.bookings b
    JOIN cd.members m ON b.memid = m.memid
WHERE m.surname = 'Farrell'
    AND m.firstname = 'David';
-- https://pgexercises.com/questions/joins/simplejoin2.html
-- "Work out the start times of bookings for tennis courts"
-- How can you produce a list of the start times for bookings for tennis courts, for the date '2012-09-21'? Return a list of start time and facility name pairings, ordered by the time.
SELECT starttime,
    name
FROM cd.bookings b
    JOIN cd.facilities f on b.facid = f.facid
WHERE starttime BETWEEN '2012-09-21' AND '2012-09-22'
    AND f.name LIKE '%Tennis Court%'
ORDER BY starttime;
-- https://pgexercises.com/questions/joins/self2.html
-- "Produce a list of all members, along with their recommender"
-- How can you output a list of all members, including the individual who recommended them (if any)? Ensure that results are ordered by (surname, firstname).
SELECT m.firstname AS memfname,
    m.surname AS memsname,
    r.firstname AS recfname,
    r.surname AS recsname
FROM cd.members m
    LEFT JOIN cd.members r ON m.recommendedby = r.memid
ORDER BY memsname,
    memfname;
-- https://pgexercises.com/questions/joins/self.html
-- "Produce a list of all members who have recommended another member"
SELECT DISTINCT firstname,
    surname
FROM cd.members
WHERE memid IN (
        SELECT recommendedby
        FROM cd.members
    )
ORDER BY surname,
    firstname;
-- "https://pgexercises.com/questions/joins/sub.html"
-- "Produce a list of all members, along with their recommender, using no joins."
-- How can you output a list of all members, including the individual who recommended them (if any), without using any joins? Ensure that there are no duplicates in the list, and that each firstname + surname pairing is formatted as a column and ordered.
SELECT DISTINCT concat(m.firstname, ' ', m.surname) as member,
    (
        SELECT concat(r.firstname, ' ', r.surname) as recommender
        FROM cd.members r
        WHERE r.memid = m.recommendedby
    )
FROM cd.members m
ORDER BY member;
-- https://pgexercises.com/questions/aggregates/count3.html
-- "Count the number of recommendations each member makes."
-- Produce a count of the number of recommendations each member has made. Order by member ID.
SELECT recommendedby,
    count(recommendedby)
FROM cd.members
WHERE recommendedby IS NOT NULL
GROUP BY recommendedby
ORDER BY recommendedby;
-- https://pgexercises.com/questions/aggregates/fachours.html
-- "List the total slots booked per facility"
-- Produce a list of the total number of slots booked per facility. For now, just produce an output table consisting of facility id and slots, sorted by facility id.
SELECT facid,
    sum(slots)
FROM cd.bookings
GROUP BY facid
ORDER BY facid;
-- https://pgexercises.com/questions/aggregates/fachoursbymonth.html
-- "List the total slots booked per facility in a given month"
-- Produce a list of the total number of slots booked per facility in the month of September 2012. Produce an output table consisting of facility id and slots, sorted by the number of slots.
SELECT facid,
    sum(slots) as "Total Slots"
FROM cd.bookings
WHERE starttime >= '2012-09-01'
    AND starttime < '2012-10-01'
GROUP BY facid
ORDER BY "Total Slots";
-- https://pgexercises.com/questions/aggregates/fachoursbymonth2.html
-- "List the total slots booked per facility per month"
-- Produce a list of the total number of slots booked per facility per month in the year of 2012. Produce an output table consisting of facility id and slots, sorted by the id and month.
SELECT facid,
    EXTRACT (
        month
        FROM starttime
    ) AS month,
    SUM(slots) AS "Total Slots"
FROM cd.bookings b
WHERE EXTRACT(
        year
        FROM starttime
    ) = 2012
GROUP BY facid,
    month
ORDER BY facid,
    month;
-- https://pgexercises.com/questions/aggregates/members1.html
-- "Find the count of members who have made at least one booking
-- Find the total number of members (including guests) who have made at least one booking.
SELECT count(distinct memid)
FROM cd.bookings b;
-- https://pgexercises.com/questions/aggregates/nbooking.html
-- "List each member's first booking after September 1st 2012"
-- Produce a list of each member name, id, and their first booking after September 1st 2012. Order by member ID.
SELECT m.surname,
    m.firstname,
    m.memid,
    MIN(b.starttime) as starttime
FROM cd.bookings b
    JOIN cd.members m ON m.memid = b.memid
WHERE starttime >= '2012-09-01'
GROUP BY m.surname,
    m.firstname,
    m.memid
ORDER BY m.memid;
-- https://pgexercises.com/questions/aggregates/countmembers.html
-- "Produce a list of member names, with each row containing the total member count"
-- Produce a list of member names, with each row containing the total member COUNT. Order by join date, and include guest members.
SELECT COUNT(*) OVER(),
    firstname,
    surname
FROM cd.members m
ORDER BY joindate;
-- https://pgexercises.com/questions/aggregates/nummembers.html
-- "Produce a numbered list of members
-- Produce a monotonically increasing numbered list of members (including guests), ordered by their date of joining. Remember that member IDs are not guaranteed to be sequential.
SELECT row_number() OVER(
        ORDER BY joindate
    ),
    firstname,
    surname
FROM cd.members m
ORDER BY joindate;
-- https://pgexercises.com/questions/aggregates/fachours4.html
-- "Output the facility id that has the highest number of slots booked, again"
-- Output the facility id that has the highest number of slots booked. Ensure that in the event of a tie, all tieing results get output.
SELECT facid,
    total
FROM (
        SELECT facid,
            sum(slots) total,
            rank() OVER(
                ORDER BY sum(slots) DESC
            ) rank
        FROM cd.bookings b
        GROUP BY facid
    ) AS ranked
WHERE rank = 1;
-- https://pgexercises.com/questions/string/concat.html
-- "Format the names of members"
-- Output the names of all members, formatted as 'Surname, Firstname'
SELECT CONCAT(surname, ', ', firstname)
FROM cd.members m;
-- https://pgexercises.com/questions/string/reg.html
-- "Find telephone numbers with parentheses"
-- You've noticed that the club's member table has telephone numbers with very inconsistent formatting. You'd like to find all the telephone numbers that contain parentheses, returning the member ID and telephone number sorted by member ID.
SELECT memid,
    telephone
FROM cd.members m
WHERE telephone LIKE '%(%)%' -- https://pgexercises.com/questions/string/substr.html
    -- "Count the number of members whose surname starts with each letter of the alphabet"
    -- You'd like to produce a count of how many members you have whose surname starts with each letter of the alphabet. Sort by the letter, and don't worry about printing out a letter if the count is 0.
SELECT SUBSTR(surname, 1, 1) as first_letter,
    COUNT(*) as count
FROM cd.members m
GROUP BY SUBSTR(surname, 1, 1)
ORDER BY first_letter;