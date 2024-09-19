# Introduction

This PostgreSQL project is centred around a fictional country club, providing a rich dataset with members, facilities, and booking histories to explore. The primary goal is to analyze facility usage and demand through practical exercises that simulate real-world database challenges. The dataset includes tables for members, facilities, and bookings, each with unique structures that reflect common issues found in real-world databases. The project includes exercises on key PostgreSQL topics — DDL (Data Definition Language), DML (Data Manipulation Language), Joins, Aggregations, and String operations.


## Table of Contents

- [Introduction](#introduction)
  - [Table of Contents](#table-of-contents)
  - [Files](#files)
  - [Schema Overview](#schema-overview)
  - [CRUD Operations](#crud-operations)
    - ["Insert some data into a table"](#insert-some-data-into-a-table)
    - ["Insert calculated data into a table"](#insert-calculated-data-into-a-table)
    - ["Update some existing data"](#update-some-existing-data)
    - ["Update a row based on the contents of another row"](#update-a-row-based-on-the-contents-of-another-row)
    - ["Delete all bookings"](#delete-all-bookings)
    - ["Delete a member FROM the cd.members table"](#delete-a-member-from-the-cdmembers-table)
  - [Select Statements](#select-statements)
    - ["Control which rows are retrieved - part 2"](#control-which-rows-are-retrieved---part-2)
    - ["Basic string searches"](#basic-string-searches)
    - ["Matching against multiple possible values"](#matching-against-multiple-possible-values)
    - ["Working with dates"](#working-with-dates)
    - ["Combining results FROM multiple queries"](#combining-results-from-multiple-queries)
  - [Join Statements](#join-statements)
    - ["Retrieve the start times of members' bookings"](#retrieve-the-start-times-of-members-bookings)
    - ["Work out the start times of bookings for tennis courts"](#work-out-the-start-times-of-bookings-for-tennis-courts)
    - ["Produce a list of all members, along with their recommender"](#produce-a-list-of-all-members-along-with-their-recommender)
    - ["Produce a list of all members who have recommended another member"](#produce-a-list-of-all-members-who-have-recommended-another-member)
    - ["Produce a list of all members, along with their recommender, using no joins"](#produce-a-list-of-all-members-along-with-their-recommender-using-no-joins)
  - [Aggregation](#aggregation)
    - ["Count the number of recommendations each member makes"](#count-the-number-of-recommendations-each-member-makes)
    - ["List the total slots booked per facility"](#list-the-total-slots-booked-per-facility)
    - ["List the total slots booked per facility in a given month"](#list-the-total-slots-booked-per-facility-in-a-given-month)
    - ["List the total slots booked per facility per month"](#list-the-total-slots-booked-per-facility-per-month)
    - ["Find the count of members who have made at least one booking](#find-the-count-of-members-who-have-made-at-least-one-booking)
    - ["List each member's first booking after September 1st 2012"](#list-each-members-first-booking-after-september-1st-2012)
    - ["Produce a list of member names, with each row containing the total member count"](#produce-a-list-of-member-names-with-each-row-containing-the-total-member-count)
    - ["Produce a numbered list of members](#produce-a-numbered-list-of-members)
    - ["Output the facility id that has the highest number of slots booked, again"](#output-the-facility-id-that-has-the-highest-number-of-slots-booked-again)
  - [String](#string)
    - ["Format the names of members"](#format-the-names-of-members)
    - ["Find telephone numbers with parentheses"](#find-telephone-numbers-with-parentheses)
    - ["Count the number of members whose surname starts with each letter of the alphabet"](#count-the-number-of-members-whose-surname-starts-with-each-letter-of-the-alphabet)

## Files

- `clubdata.sql` : initializing the PSQL database with data
- `queries.sql` : all the queries you might find in this Readme.

## Schema Overview

The **Members** table stores information such as ID, address, membership timestamp, and referrer details. The **Facilities** table lists bookable amenities, capturing details like booking costs, construction expenses, and maintenance estimates, helping the club assess the financial viability of each facility. The **Bookings** table, which logs facility usage by members, stores the facility id, the member who made the booking, the start of the booking, and how many half hour 'slots' the booking was made for. It is used as a junction table or join table and is typically used in many-to-many relationships.

![Membership DB Schema](assets/schema-horizontal.svg)

```sql
CREATE TABLE cd.members (
  memid INTEGER NOT NULL, 
  surname VARCHAR(200) NOT NULL, 
  firstname VARCHAR(200) NOT NULL, 
  address VARCHAR(300) NOT NULL, 
  zipcode INTEGER NOT NULL, 
  telephone VARCHAR(20) NOT NULL, 
  recommendedby INTEGER, 
  joindate TIMESTAMP NOT NULL, 
  CONSTRAINT members_pk PRIMARY KEY (memid), 
  CONSTRAINT fk_members_recommendedby FOREIGN KEY (recommendedby) 
  REFERENCES cd.members(memid) ON DELETE SET NULL
);
```

`cd.members` has a primary key constraint on memid, ensuring each member has a unique identifier. Additionally, the recommendedby field has a foreign key constraint that references the memid of `cd.bookings`. **ON DELETE SET NULL** ensures that if the referenced member is deleted, the recommendedby field will be set to NULL rather than causing an error or cascading the delete.

```sql
CREATE TABLE cd.bookings
(
    bookid INTEGER NOT NULL,
    facid INTEGER NOT NULL,
    memid INTEGER NOT NULL,
    starttime TIMESTAMP NOT NULL,
    slot INTEGER NOT NULL,
    CONSTRAINT bookings_pk PRIMARY KEY (bookid),
    CONSTRAINT fk_bookings_facid FOREIGN KEY (facid)
    REFERENCES cd.facilities(facid),
    CONSTRAINT fk_bookings_memid FOREIGN KEY (memid)
    REFERENCES cd.members(memid)
);
```

Note that `cd.bookings` has a primary key constraint on bookid, ensuring each booking has a unique identifier. There are also two foreign key constraints: facid references `cd.facilities(facid)`, linking each booking to a specific facility, and memid references `cd.members(memid)`, linking each booking to a specific member.

```sql
CREATE TABLE cd.facilities
(
    facid INTEGER NOT NULL,
    name VARCHAR(100) NOT NULL,
    membercost NUMERIC NOT NULL,
    guestcost NUMERIC NOT NULL,
    initialoutlay NUMERIC NOT NULL,
    monthlymaintenance NUMERIC NOT NULL,
    CONSTRAINT facilities_pk PRIMARY KEY (facid)
);
```

Lastly, `cd.facilities` has a primary key constraint on facid, ensuring each facility has a unique identifier. It also helps `cd.bookings` find information about the facilities.

## CRUD Operations

![Membership DB Schema](assets/schema-horizontal.svg)

### ["Insert some data into a table"](https://pgexercises.com/questions/updates/insert.html)

The club is adding a new facility - a spa. We need to add it into the facilities table. Use the following values:
*facid: 9, Name: 'Spa', membercost: 20, guestcost: 30, initialoutlay: 100000, monthlymaintenance: 800.*

```sql
INSERT INTO cd.facilities
    (
    facid, name, membercost, guestcost,
    initialoutlay, monthlymaintenance
    )
VALUES
    (9, 'Spa', 20, 30, 100000, 800);
```

### ["Insert calculated data into a table"](https://pgexercises.com/questions/updates/insert3.html)

Let's try adding the spa to the facilities table again. This time, though, we want to automatically generate the value for the next facid, rather than specifying it as a constant. Use the following values for everything else: *Name: 'Spa', membercost: 20, guestcost: 30, initialoutlay: 100000, monthlymaintenance: 800.*

```sql
INSERT INTO cd.facilities
    (
    facid, name, membercost, guestcost,
    initialoutlay, monthlymaintenance
    )
VALUES
    (
        (
        SELECT
            max(facid)
        FROM
            cd.facilities
        ) + 1,
        'Spa',
        20,
        30,
        100000,
        800
  );
```

### ["Update some existing data"](https://pgexercises.com/questions/updates/update.html)

We made a mistake when entering the data for the second tennis court. The initial outlay was 10000 rather than 8000: you need to alter the data to fix the error.

```sql
UPDATE
    cd.facilities
SET
    initialoutlay = 10000
WHERE
    facid = 1;
```

### ["Update a row based on the contents of another row"](https://pgexercises.com/questions/updates/updatecalculated.html)

We want to alter the price of the second tennis court so that it costs 10% more than the first one. Try to do this without using constant values for the prices, so that we can reuse the statement if we want to.

```sql
UPDATE
    cd.facilities facs
SET
    membercost = tc1.membercost * 1.1,
    guestcost = tc1.guestcost * 1.1
FROM
(
    SELECT
        *
    FROM
        cd.facilities
    WHERE
        facid = 0
) tc1
WHERE
    facs.facid = 1;
```

### ["Delete all bookings"](https://pgexercises.com/questions/updates/delete.html)

As part of a clearout of our database, we want to delete all bookings FROM the cd.bookings table. How can we accomplish this?

```sql
DELETE FROM cd.bookings
```

### ["Delete a member FROM the cd.members table"](https://pgexercises.com/questions/updates/deletewh.html)

We want to remove member 37, who has never made a booking, FROM our database. How can we achieve that?

```sql
DELETE FROM
    cd.members
WHERE
    memid = 37;
```

## Select Statements

![Membership DB Schema](assets/schema-horizontal.svg)

### ["Control which rows are retrieved - part 2"](https://pgexercises.com/questions/basic/where2.html)

How can you produce a list of facilities that charge a fee to members, and that fee is less than 1/50th of the monthly maintenance cost? Return the facid, facility name, member cost, and monthly maintenance of the facilities in question.

```sql
SELECT
    facid,
    name,
    membercost,
    monthlymaintenance
FROM
    cd.facilities
WHERE
    membercost > 0
    AND membercost < monthlymaintenance / 50;
```

### ["Basic string searches"](https://pgexercises.com/questions/basic/where3.html)

How can you produce a list of all facilities with the word 'Tennis' in their name?

```sql
SELECT
    *
FROM
    cd.facilities
WHERE
    name LIKE '%Tennis%';
```

### ["Matching against multiple possible values"](https://pgexercises.com/questions/basic/where4.html)

How can you retrieve the details of facilities with ID 1 and 5? Try to do it without using the OR operator.

```sql
SELECT
    *
FROM
    cd.facilities
WHERE
    facid in (1, 5);
```

### ["Working with dates"](https://pgexercises.com/questions/basic/date.html)

How can you produce a list of members who joined after the start of September 2012? Return the memid, surname, firstname, and joindate of the members in question.

```sql
SELECT
    memid,
    surname,
    firstname,
    joindate
FROM
    cd.members
WHERE
    joindate > '2012-09-01';
```

### ["Combining results FROM multiple queries"](https://pgexercises.com/questions/basic/union.html)

You, for some reason, want a combined list of all surnames and all facility names. Yes, this is a contrived example :-). Produce that list!

```sql
SELECT
    surname
FROM
    cd.members
UNION
SELECT
    name
FROM
    cd.facilities;
```

## Join Statements

![Membership DB Schema](assets/schema-horizontal.svg)

### ["Retrieve the start times of members' bookings"](https://pgexercises.com/questions/joins/simplejoin.html)

How can you produce a list of the start times for bookings by members named 'David Farrell'?

```sql
SELECT
    starttime
FROM
    cd.bookings b
    JOIN cd.members m ON b.memid = m.memid
WHERE
    m.surname = 'Farrell'
    AND m.firstname = 'David';
```

### ["Work out the start times of bookings for tennis courts"](https://pgexercises.com/questions/joins/simplejoin2.html)

How can you produce a list of the start times for bookings for tennis courts, for the date '2012-09-21'? Return a list of start time and facility name pairings, ordered by the time.

```sql
SELECT
    starttime,
    name
FROM
    cd.bookings b
    JOIN cd.facilities f on b.facid = f.facid
WHERE
    starttime BETWEEN '2012-09-21'
    AND '2012-09-22'
    AND f.name LIKE '%Tennis Court%'
ORDER BY
    starttime;
```

### ["Produce a list of all members, along with their recommender"](https://pgexercises.com/questions/joins/self2.html)

How can you output a list of all members, including the individual who recommended them (if any)? Ensure that results are ordered by (surname, firstname).

```sql
SELECT
    m.firstname AS memfname,
    m.surname AS memsname,
    r.firstname AS recfname,
    r.surname AS recsname
FROM
    cd.members m
    LEFT JOIN cd.members r ON m.recommendedby = r.memid
ORDER BY
    memsname,
    memfname;
```

### ["Produce a list of all members who have recommended another member"](https://pgexercises.com/questions/joins/self.html)

```sql
SELECT
    DISTINCT firstname,
    surname
FROM
    cd.members
WHERE
    memid IN (
    SELECT
        recommendedby
    FROM
        cd.members
    )
ORDER BY
    surname,
    firstname;
```

### ["Produce a list of all members, along with their recommender, using no joins"](https://pgexercises.com/questions/joins/sub.html)

How can you output a list of all members, including the individual who recommended them (if any), without using any joins? Ensure that there are no duplicates in the list, and that each firstname + surname pairing is formatted as a column and ordered.

```sql
SELECT
    DISTINCT concat(m.firstname, ' ', m.surname) as member,
    (
    SELECT
        concat(r.firstname, ' ', r.surname) as recommender
    FROM
        cd.members r
    WHERE
        r.memid = m.recommendedby
    )
FROM
    cd.members m
ORDER BY
    member;
```

## Aggregation

![Membership DB Schema](assets/schema-horizontal.svg)

### ["Count the number of recommendations each member makes"](https://pgexercises.com/questions/aggregates/count3.html)

Produce a count of the number of recommendations each member has made. Order by member ID.

```sql
SELECT
    recommendedby,
    count(recommendedby)
FROM
    cd.members
WHERE
    recommendedby IS NOT NULL
GROUP BY
    recommendedby
ORDER BY
    recommendedby;
```

### ["List the total slots booked per facility"](https://pgexercises.com/questions/aggregates/fachours.html)

Produce a list of the total number of slots booked per facility. For now, just produce an output table consisting of facility id and slots, sorted by facility id.

```sql
SELECT
    facid,
    sum(slots)
FROM
    cd.bookings
GROUP BY
    facid
ORDER BY
    facid;
```

### ["List the total slots booked per facility in a given month"](https://pgexercises.com/questions/aggregates/fachoursbymonth.html)

Produce a list of the total number of slots booked per facility in the month of September 2012. Produce an output table consisting of facility id and slots, sorted by the number of slots.

```sql
SELECT
    facid,
    sum(slots) as "Total Slots"
FROM
    cd.bookings
WHERE
    starttime >= '2012-09-01'
    AND starttime < '2012-10-01'
GROUP BY
    facid
ORDER BY
    "Total Slots";
```

### ["List the total slots booked per facility per month"](https://pgexercises.com/questions/aggregates/fachoursbymonth2.html)

Produce a list of the total number of slots booked per facility per month in the year of 2012. Produce an output table consisting of facility id and slots, sorted by the id and month.

```sql
SELECT
    facid,
    EXTRACT (
        month
        FROM
        starttime
    ) AS month,
    SUM(slots) AS "Total Slots"
FROM
    cd.bookings b
WHERE
    EXTRACT(
        year
        FROM
        starttime
    ) = 2012
GROUP BY
    facid,
    month
ORDER BY
    facid,
    month;
```

### ["Find the count of members who have made at least one booking](https://pgexercises.com/questions/aggregates/members1.html)

Find the total number of members (including guests) who have made at least one booking.

```sql
SELECT
    COUNT(DISTINCT memid)
FROM
    cd.bookings b;
```

### ["List each member's first booking after September 1st 2012"](https://pgexercises.com/questions/aggregates/nbooking.html)

Produce a list of each member name, id, and their first booking after September 1st 2012. Order by member ID.

```sql
SELECT
    m.surname,
    m.firstname,
    m.memid,
    MIN(b.starttime) AS starttime
FROM
    cd.bookings b
    JOIN cd.members m ON m.memid = b.memid
WHERE
    starttime >= '2012-09-01'
GROUP BY
    m.surname,
    m.firstname,
    m.memid
ORDER BY
    m.memid;
```

### ["Produce a list of member names, with each row containing the total member count"](https://pgexercises.com/questions/aggregates/countmembers.html)

Produce a list of member names, with each row containing the total member COUNT. Order by join date, and include guest members.

```sql
SELECT
    COUNT(*) OVER(),
    firstname,
    surname
FROM
    cd.members m
ORDER BY
    joindate;
```

### ["Produce a numbered list of members](https://pgexercises.com/questions/aggregates/nummembers.html)

Produce a monotonically increasing numbered list of members (including guests), ordered by their date of joining. Remember that member IDs are not guaranteed to be sequential.

```sql
SELECT
    row_number() OVER(
    ORDER BY
        joindate
    ),
    firstname,
    surname
FROM
    cd.members m
ORDER BY
    joindate;
```

### ["Output the facility id that has the highest number of slots booked, again"](https://pgexercises.com/questions/aggregates/fachours4.html)

Output the facility id that has the highest number of slots booked. Ensure that in the event of a tie, all tieing results get output.

```sql
SELECT
    facid,
    total
FROM
    (
    SELECT
        facid,
        sum(slots) total,
        rank() OVER(
        ORDER BY
            sum(slots) DESC
        ) rank
    FROM
        cd.bookings b
    GROUP BY
        facid
  ) AS ranked
WHERE
    rank = 1;
```

## String

![Membership DB Schema](assets/schema-horizontal.svg)

### ["Format the names of members"](https://pgexercises.com/questions/string/concat.html)

Output the names of all members, formatted as 'Surname, Firstname'

```sql
SELECT
    CONCAT(surname, ', ', firstname)
FROM
    cd.members m;
```

### ["Find telephone numbers with parentheses"](https://pgexercises.com/questions/string/reg.html)

You've noticed that the club's member table has telephone numbers with very inconsistent formatting. You'd like to find all the telephone numbers that contain parentheses, returning the member ID and telephone number sorted by member ID.

```sql
SELECT
    memid,
    telephone
FROM
    cd.members m
WHERE
    telephone LIKE '%(%)%'
```

### ["Count the number of members whose surname starts with each letter of the alphabet"](https://pgexercises.com/questions/string/substr.html)

You'd like to produce a count of how many members you have whose surname starts with each letter of the alphabet. Sort by the letter, and don't worry about printing out a letter if the count is 0.

```sql
SELECT
    SUBSTR(surname, 1, 1) as first_letter,
    COUNT(*) as count
FROM
    cd.members m
GROUP BY
    SUBSTR(surname, 1, 1)
ORDER BY
    first_letter;
```
