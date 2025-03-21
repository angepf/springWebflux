INSERT INTO S_CHALLENGE.T_PERSONS (name, gender, address, phone, identification) VALUES
                                                                                     ('Jose Lema', 'MALE', 'Otavalo sn y principal', '098254785', '1705201446'),
                                                                                     ('Marianela Montalvo', 'FEMALE', 'Amazonas y NNUU', '097548965', '0101503206'),
                                                                                     ('Juan Osorio', 'MALE', '13 junio y Equinoccial', '098874587', '0502648725');

-- Insert initial data into Customers based on Personas
INSERT INTO S_CHALLENGE.T_CUSTOMERS (identification, password, status) VALUES
                                                                           ((SELECT identification FROM S_CHALLENGE.T_PERSONS WHERE name='Jose Lema'), '1234', 'ACTIVE'),
                                                                           ((SELECT identification FROM S_CHALLENGE.T_PERSONS WHERE name='Marianela Montalvo'), '5678', 'ACTIVE'),
                                                                           ((SELECT identification FROM S_CHALLENGE.T_PERSONS WHERE name='Juan Osorio'), '1245', 'INACTIVE');
