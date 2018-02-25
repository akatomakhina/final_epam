CREATE TABLE IF NOT EXISTS catalog (
  id_catalog INT NOT NULL,
  catalog_name VARCHAR(70) NOT NULL,
  parent INT,
  PRIMARY KEY (id_catalog)
);


CREATE TABLE IF NOT EXISTS status(
  id_status INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(70) NOT NULL,
  PRIMARY KEY (id_status),
);


CREATE TABLE IF NOT EXISTS role(
  id_role INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(70) NOT NULL,
  PRIMARY KEY (id_role)
);


CREATE TABLE IF NOT EXISTS shop_client (
  id_client INT NOT NULL AUTO_INCREMENT,
  firstname VARCHAR(70),
  lastname  VARCHAR(70),
  login     VARCHAR(40),
  email     VARCHAR(120) NOT NULL,
  id_role   INT NOT NULL,
  password  VARCHAR(100),
  balance   DOUBLE NULL DEFAULT 0,
  banned    TINYINT(1) NULL DEFAULT 0,
  PRIMARY KEY (id_client),
  FOREIGN KEY (id_role) REFERENCES role(id_role),
  UNIQUE (email)
);


CREATE TABLE IF NOT EXISTS shop_order(
  id_order INT NOT NULL AUTO_INCREMENT,
  date datetime NOT NULL,
  id_client INT NOT NULL,
  amount INT NOT NULL,
  id_status INT DEFAULT 1,
  PRIMARY KEY (id_order),
  FOREIGN KEY (id_status) REFERENCES status(id_status),
  FOREIGN KEY (id_client) REFERENCES shop_client(id_client)
);


CREATE TABLE IF NOT EXISTS shop_product (
  id_product INT NOT NULL AUTO_INCREMENT,
  title VARCHAR(100) NOT NULL,
  price DOUBLE NOT NULL,
  description VARCHAR(500) NULL DEFAULT 'Very good product',
  vendor VARCHAR(100) NULL,
  id_catalog INT NOT NULL,
  PRIMARY KEY (id_product),
  FOREIGN KEY (id_catalog) REFERENCES catalog (id_catalog)
);


CREATE TABLE IF NOT EXISTS basket (
  id_basket INT NOT NULL,
  id_client INT NOT NULL,
  id_product INT NOT NULL,
  amount INT NULL,
  PRIMARY KEY (id_basket),
    FOREIGN KEY (id_product) REFERENCES shop_product (id_product),
    FOREIGN KEY (id_client) REFERENCES shop_client (id_client)
);


CREATE TABLE IF NOT EXISTS warehouse(
  id_wrehouse INT NOT NULL,
  id_product INT NOT NULL,
  amount INT NULL,
  PRIMARY KEY (id_wrehouse, id_product),
  FOREIGN KEY (id_product) REFERENCES shop_product (id_product)
);


CREATE TABLE IF NOT EXISTS order_items(
  id_order_iteams INT NOT NULL,
  id_product INT NOT NULL,
  id_order INT NOT NULL,
  amount INT NULL,
  PRIMARY KEY (id_order_iteams, id_product, id_order),
  FOREIGN KEY (id_order) REFERENCES shop_order (id_order),
  FOREIGN KEY (id_product) REFERENCES shop_product (id_product)
);


INSERT INTO ROLE (id_role, name)
values(1, 'Admin'),
  (2, 'User');

INSERT INTO `shop_client` (`firstname`, `lastname`, `login`, `password`, `email`, `id_role`)
VALUES ('Anastasiya', 'Katomakhina', 'nastichka', '123123123', 'katom@gmail.com', 2),
  ('Tatsiana', 'Rikun', 'Batman', 'aaa222aaa', 'tanyushka111@yahoo.com', 2),
  ('Darya', 'Beloded', 'losos', 'bbb333bbb', 'd.a.r.k.Angel@mail.ru', 2),
  ('Vladzislav', 'Eserskiy', 'vlad256', '365fhj', 'e.zer@gmail.com', 2),
  ('Anton', 'Muradhanov', 'xaker', '7yghdte65u', 'kartoshka@mail.ru', 2);

INSERT INTO `catalog` (`id_catalog`, `parent`, `catalog_name`) VALUES ('1', null, 'clothes');
INSERT INTO `catalog` (`id_catalog`, `parent`, `catalog_name`) VALUES ('2', null, 'shoes');
INSERT INTO `catalog` (`id_catalog`, `parent`, `catalog_name`) VALUES ('3', null, 'cosmetics');
INSERT INTO `catalog` (`id_catalog`, `parent`, `catalog_name`) VALUES ('4', '1', 'skirts');
INSERT INTO `catalog` (`id_catalog`, `parent`, `catalog_name`) VALUES ('5', '1', 'swimsuits');
INSERT INTO `catalog` (`id_catalog`, `parent`, `catalog_name`) VALUES ('6', '2', 'sandals');
INSERT INTO `catalog` (`id_catalog`, `parent`, `catalog_name`) VALUES ('7', '2', 'keds');
INSERT INTO `catalog` (`id_catalog`, `parent`, `catalog_name`) VALUES ('8', '3', 'lipsticks');
INSERT INTO `catalog` (`id_catalog`, `parent`, `catalog_name`) VALUES ('9', '3', 'powders');


INSERT INTO shop_product(`title`, `description`, `vendor`, `price`, `id_catalog`)
VALUES ('pleated skirt', 'Calf-length skirt in crêped, woven fabric with an elasticized waistband, pleats, and rounded hem.', 'H&M', 40.37, 1),
  ('bell-shaped skirt', 'Short, bell-shaped skirt in stretch jersey with wide elastication at the waist. Unlined.', 'H&M', 33.25, 1),
  ('jersey pencil skirt', 'Knee-length skirt in thick, viscose-blend jersey with concealed elastication at top and a slit at back.', 'Terranova', 24.15, 1),
  ('v-neck swimsuit', 'Fully lined, V-neck swimsuit with lightly padded cups. Narrow, adjustable shoulder straps.', 'H&M', 29.99, 1),
  ('swimsuit','Fully lined swimsuit with a texture-patterned finish. Lightly padded cups, concealed elastication below bust, and low-cut back.','H&M', 34.99, 1),
  ('swimsuit high leg', 'Fully lined swimsuit with a shimmering metallic finish. Narrow, adjustable shoulder straps, low-cut back, and high-cut legs.', 'Terranova', 39.99, 1),
  ('Scuba-look Bikini Top', 'Fully lined bikini top in scuba-look fabric with wide shoulder straps and no fasteners.', 'Calliope', 24.99, 1),
  ('Leather sandals', 'PREMIUM QUALITY. Sandals in grained leather with adjustable foot straps with metal buckles and concealed elastication. Adjustable heel straps with a hook and loop fastening and a loop at the back. Moulded suede insoles and fluted rubber soles.', 'H&M', 29.99, 2),
  ('Satin Sandals', 'Satin sandals with covered heels, adjustable ankle strap with metal buckle, and strap at front decorated with faceted plastic beads. Faux leather insoles and rubber soles. Front platform height 3/4 in. Heel height 2 1/4 in.', 'Calliope', 39.99, 2),
  ('Sandals', 'Sandals with plastic decorations at front, adjustable ankle strap with metal buckle, and covered heels. Faux leather lining and insoles. Rubber soles. Heel height 2 1/4 in.', 'Calliope', 49.99, 2),
  ('Suede sandals', 'PREMIUM QUALITY. Suede toe-post sandals with leather cords that tie around the ankle. Leather linings and insoles and rubber soles.', 'Terranova', 24.99, 2),
  ('Toe-post sandals', 'Toe-post sandals with a decorative knot at the front and an adjustable heel strap. Imitation leather insoles and rubber soles.', 'Terranova', 6.99, 2),
  ('Patent Boots', 'Boots in faux patent leather with lacing at front and loop at back. Fabric lining, fabric insoles, and chunky rubber soles. Front platform height 3/4 in., heel height 1 3/4 in.', 'Marko', 49.99, 2),
  ('Platform Ankle Boots', 'Platform ankle boots in faux leather with lacing at front, zip at back, and covered heels. Fabric lining, fabric insoles, and rubber soles. Front platform height 1 1/4 in. Heel height 4 1/2 in.', 'Belwest', 49.99, 2),
  ('Ankle boots', 'Ankle boots with lacing at the front, a loop at the back, fabric linings, imitation leather insoles and chunky rubber soles. Platform front 2 cm, heel 4.5 cm.', 'Marko', 39.99, 2),
  ('Warm-lined Boots', 'Warm-lined boots in faux leather and faux suede with a padded edge, lacing at front, and loop at back. Pile lining, faux leather insoles, and chunky rubber soles. Heel height 1 3/4 in.', 'Tamaris', 69.99, 2),
  ('Ankle Boots with Soft Leg', 'Ankle boots with round toes and covered heels. Soft leg section. Fabric lining, fabric insoles, and rubber soles. Heel height 3 1/4 in.', 'Tamaris', 49.99, 2),
  ('Hydrating Sheer Lipshine', 'A lightweight, innovative lipstick with intensified pigments that take colour and sheen to a new level of vibrancy.', 'Chanel', 37.00, 3),
  ('Luminous Matte Lip Colour', 'This non-drying matte lipstick glides on lips with intensely rich, long-wearing colour and a sumptuously soft, velvet matte finish.', 'Chanel', 37.00, 3),
  ('Ultra Wear Lip Colour', 'Glorious long-lasting colour pairs with high-shine gloss to deliver full-coverage lip colour that lasts all day.', 'Clarins', 32.00, 3),
  ('Jumbo Longwear Lip Crayon', 'An easy-to-apply lipstick that delivers lasting, full-coverage colour and satiny shine in a smooth, comfortable texture. The twist-up retractable crayon offers convenient, on-the-go shading and impeccable touch-ups.', 'Avon', 23.00, 3),
  ('Complete Care Lipshine', 'The intensity of a lipstick, the shine of a lipgloss and the comfort of a lip balm — all in one creamy yet lightweight formula.', 'Avon', 12.75, 3),
  ('Matte Liquid Lip Colour', 'A long-lasting liquid lipstick with a smooth texture that glides on like a gloss and sets with a luminous, velvet-like finish.', 'Claris', 39.00, 3),
  ('Natural Finish Loose Powder', 'An ultra-soft, loose powder that provides sheer, lightweight coverage, POUDRE UNIVERSELLE LIBRE helps to even skin tone, and set and perfect makeup for a natural, matte finish.', 'Nyx', 13.99, 3),
  ('Silver Reflections Shimmering Powder', 'A highlighting powder softly illuminates the face anddécolleté with a shimmering, pearlescent glow.', 'Burberry', 15.99, 3),
  ('Highlighting Powder', 'An illuminating face powder captures and reflects light to leave a sheer, radiant finish. In shimmery gold shades, with ivory, bronze and rose tints. The handbag-friendly compact (with a brush applicator) offers convenient touch-ups in a flash.', 'Burberry', 18.75, 3),
  ('Blotting Papers', 'Nestled in an elegant, portable mirrored case, PAPIER MATIFIANT DE CHANEL blotting papers instantly mattify skin by absorbing excess oil. Comes in a pad of 150 papers.', 'Chanel', 52.00, 3)

