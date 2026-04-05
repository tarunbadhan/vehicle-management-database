-- INSERT
INSERT INTO Owner VALUES
(3, 'Sara Khan', '506-555-6666', 'sara@email.com', 'Moncton, NB');

-- SELECT
SELECT v.vehicle_id, v.brand, v.model, o.full_name
FROM Vehicle v
JOIN Owner o ON v.owner_id = o.owner_id;

-- UPDATE
UPDATE Vehicle
SET color = 'Red'
WHERE vehicle_id = 102;

-- DELETE
DELETE FROM ServiceRecord
WHERE service_id = 1002;

-- VIEW ALL
SELECT * FROM Owner;
SELECT * FROM Vehicle;
SELECT * FROM ServiceRecord;