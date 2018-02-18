-- 5) Registration Cart
-- Displays the name of the courses in the cart that have a status of 
-- 'registered'.
SELECT
ci.course_name

FROM course_information ci
WHERE id = (
  SELECT cinst.course_id
  FROM course_sections cinst
  WHERE cinst.id = (
    SELECT ic.instance_id
    FROM instructor_course_link_cart ic
    WHERE ic.instructor_id
    IN (
    	SELECT id
    	FROM instructors
    	WHERE user_id = :userId
    )
  )
)
ORDER BY ci.course_name ASC;
