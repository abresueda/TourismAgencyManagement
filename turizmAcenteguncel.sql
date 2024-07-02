PGDMP     ;                    |            tourismagency    13.15    13.15 1    �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    32775    tourismagency    DATABASE     k   CREATE DATABASE tourismagency WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'Turkish_T�rkiye.1254';
    DROP DATABASE tourismagency;
                postgres    false            �            1259    32784    hotel    TABLE     �   CREATE TABLE public.hotel (
    hotel_id integer NOT NULL,
    hotel_name text,
    hotel_city text,
    hotel_region text,
    hotel_address text,
    hotel_email text,
    hotel_phone text,
    hotel_star integer
);
    DROP TABLE public.hotel;
       public         heap    postgres    false            �            1259    32831    hotel_features    TABLE     �   CREATE TABLE public.hotel_features (
    hotel_id integer NOT NULL,
    parking text,
    wifi text,
    pool text,
    fitness text,
    concierge text,
    spa text,
    room_service text
);
 "   DROP TABLE public.hotel_features;
       public         heap    postgres    false            �            1259    32821    hotel_hotel_id_seq    SEQUENCE     �   ALTER TABLE public.hotel ALTER COLUMN hotel_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.hotel_hotel_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    200            �            1259    32870    pension    TABLE     "  CREATE TABLE public.pension (
    pension_type_id integer NOT NULL,
    hotel_id integer,
    ultra_all_inclusive boolean,
    all_inclusive boolean,
    bed_and_breakfast boolean,
    full_board boolean,
    half_board boolean,
    bed_only boolean,
    non_alcohol_full_credit boolean
);
    DROP TABLE public.pension;
       public         heap    postgres    false            �            1259    32868 !   pension_types_pension_type_id_seq    SEQUENCE     �   CREATE SEQUENCE public.pension_types_pension_type_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 8   DROP SEQUENCE public.pension_types_pension_type_id_seq;
       public          postgres    false    206            �           0    0 !   pension_types_pension_type_id_seq    SEQUENCE OWNED BY     a   ALTER SEQUENCE public.pension_types_pension_type_id_seq OWNED BY public.pension.pension_type_id;
          public          postgres    false    205            �            1259    32886    period    TABLE     }   CREATE TABLE public.period (
    period_id integer NOT NULL,
    hotel_id integer,
    start_date date,
    end_date date
);
    DROP TABLE public.period;
       public         heap    postgres    false            �            1259    32884    period_period_id_seq    SEQUENCE     �   CREATE SEQUENCE public.period_period_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 +   DROP SEQUENCE public.period_period_id_seq;
       public          postgres    false    208            �           0    0    period_period_id_seq    SEQUENCE OWNED BY     M   ALTER SEQUENCE public.period_period_id_seq OWNED BY public.period.period_id;
          public          postgres    false    207            �            1259    32910    reservation    TABLE     I  CREATE TABLE public.reservation (
    rez_id integer NOT NULL,
    hotel_id integer,
    room_id integer,
    customer_name character varying(50),
    citizen_number character varying(30),
    email character varying(100),
    phone_number character varying(20),
    check_in date,
    check_out date,
    total_price numeric
);
    DROP TABLE public.reservation;
       public         heap    postgres    false            �            1259    32928    reservation_rez_id_seq    SEQUENCE     �   ALTER TABLE public.reservation ALTER COLUMN rez_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.reservation_rez_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    211            �            1259    32899    room    TABLE     #  CREATE TABLE public.room (
    id integer NOT NULL,
    hotel_id integer NOT NULL,
    room_type character varying(50) NOT NULL,
    pension_type character varying(50) NOT NULL,
    period character varying(50) NOT NULL,
    adult_price numeric(10,2) NOT NULL,
    child_price numeric(10,2) NOT NULL,
    stock integer NOT NULL,
    bed_count integer NOT NULL,
    squaremeters integer NOT NULL,
    tv boolean NOT NULL,
    minibar boolean NOT NULL,
    game_console boolean NOT NULL,
    safe boolean NOT NULL,
    projector boolean NOT NULL
);
    DROP TABLE public.room;
       public         heap    postgres    false            �            1259    32897    room_id_seq    SEQUENCE     �   CREATE SEQUENCE public.room_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 "   DROP SEQUENCE public.room_id_seq;
       public          postgres    false    210            �           0    0    room_id_seq    SEQUENCE OWNED BY     ;   ALTER SEQUENCE public.room_id_seq OWNED BY public.room.id;
          public          postgres    false    209            �            1259    32805    user    TABLE     }   CREATE TABLE public."user" (
    user_id integer NOT NULL,
    user_name text,
    user_password text,
    user_role text
);
    DROP TABLE public."user";
       public         heap    postgres    false            �            1259    32819    user_user_id_seq    SEQUENCE     �   ALTER TABLE public."user" ALTER COLUMN user_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.user_user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    201            H           2604    32873    pension pension_type_id    DEFAULT     �   ALTER TABLE ONLY public.pension ALTER COLUMN pension_type_id SET DEFAULT nextval('public.pension_types_pension_type_id_seq'::regclass);
 F   ALTER TABLE public.pension ALTER COLUMN pension_type_id DROP DEFAULT;
       public          postgres    false    206    205    206            I           2604    32889    period period_id    DEFAULT     t   ALTER TABLE ONLY public.period ALTER COLUMN period_id SET DEFAULT nextval('public.period_period_id_seq'::regclass);
 ?   ALTER TABLE public.period ALTER COLUMN period_id DROP DEFAULT;
       public          postgres    false    208    207    208            J           2604    32902    room id    DEFAULT     b   ALTER TABLE ONLY public.room ALTER COLUMN id SET DEFAULT nextval('public.room_id_seq'::regclass);
 6   ALTER TABLE public.room ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    210    209    210            �          0    32784    hotel 
   TABLE DATA           �   COPY public.hotel (hotel_id, hotel_name, hotel_city, hotel_region, hotel_address, hotel_email, hotel_phone, hotel_star) FROM stdin;
    public          postgres    false    200   �;       �          0    32831    hotel_features 
   TABLE DATA           n   COPY public.hotel_features (hotel_id, parking, wifi, pool, fitness, concierge, spa, room_service) FROM stdin;
    public          postgres    false    204   �<       �          0    32870    pension 
   TABLE DATA           �   COPY public.pension (pension_type_id, hotel_id, ultra_all_inclusive, all_inclusive, bed_and_breakfast, full_board, half_board, bed_only, non_alcohol_full_credit) FROM stdin;
    public          postgres    false    206   �<       �          0    32886    period 
   TABLE DATA           K   COPY public.period (period_id, hotel_id, start_date, end_date) FROM stdin;
    public          postgres    false    208   (=       �          0    32910    reservation 
   TABLE DATA           �   COPY public.reservation (rez_id, hotel_id, room_id, customer_name, citizen_number, email, phone_number, check_in, check_out, total_price) FROM stdin;
    public          postgres    false    211   h=       �          0    32899    room 
   TABLE DATA           �   COPY public.room (id, hotel_id, room_type, pension_type, period, adult_price, child_price, stock, bed_count, squaremeters, tv, minibar, game_console, safe, projector) FROM stdin;
    public          postgres    false    210   8>       �          0    32805    user 
   TABLE DATA           N   COPY public."user" (user_id, user_name, user_password, user_role) FROM stdin;
    public          postgres    false    201   �?       �           0    0    hotel_hotel_id_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.hotel_hotel_id_seq', 22, true);
          public          postgres    false    203            �           0    0 !   pension_types_pension_type_id_seq    SEQUENCE SET     O   SELECT pg_catalog.setval('public.pension_types_pension_type_id_seq', 2, true);
          public          postgres    false    205            �           0    0    period_period_id_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('public.period_period_id_seq', 6, true);
          public          postgres    false    207            �           0    0    reservation_rez_id_seq    SEQUENCE SET     D   SELECT pg_catalog.setval('public.reservation_rez_id_seq', 9, true);
          public          postgres    false    212            �           0    0    room_id_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.room_id_seq', 11, true);
          public          postgres    false    209            �           0    0    user_user_id_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.user_user_id_seq', 8, true);
          public          postgres    false    202            P           2606    32838 "   hotel_features hotel_features_pkey 
   CONSTRAINT     f   ALTER TABLE ONLY public.hotel_features
    ADD CONSTRAINT hotel_features_pkey PRIMARY KEY (hotel_id);
 L   ALTER TABLE ONLY public.hotel_features DROP CONSTRAINT hotel_features_pkey;
       public            postgres    false    204            L           2606    32788    hotel hotel_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.hotel
    ADD CONSTRAINT hotel_pkey PRIMARY KEY (hotel_id);
 :   ALTER TABLE ONLY public.hotel DROP CONSTRAINT hotel_pkey;
       public            postgres    false    200            R           2606    32875    pension pension_types_pkey 
   CONSTRAINT     e   ALTER TABLE ONLY public.pension
    ADD CONSTRAINT pension_types_pkey PRIMARY KEY (pension_type_id);
 D   ALTER TABLE ONLY public.pension DROP CONSTRAINT pension_types_pkey;
       public            postgres    false    206            T           2606    32891    period period_pkey 
   CONSTRAINT     W   ALTER TABLE ONLY public.period
    ADD CONSTRAINT period_pkey PRIMARY KEY (period_id);
 <   ALTER TABLE ONLY public.period DROP CONSTRAINT period_pkey;
       public            postgres    false    208            X           2606    32917    reservation reservation_pkey 
   CONSTRAINT     ^   ALTER TABLE ONLY public.reservation
    ADD CONSTRAINT reservation_pkey PRIMARY KEY (rez_id);
 F   ALTER TABLE ONLY public.reservation DROP CONSTRAINT reservation_pkey;
       public            postgres    false    211            V           2606    32904    room room_pkey 
   CONSTRAINT     L   ALTER TABLE ONLY public.room
    ADD CONSTRAINT room_pkey PRIMARY KEY (id);
 8   ALTER TABLE ONLY public.room DROP CONSTRAINT room_pkey;
       public            postgres    false    210            N           2606    32812    user users_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public."user"
    ADD CONSTRAINT users_pkey PRIMARY KEY (user_id);
 ;   ALTER TABLE ONLY public."user" DROP CONSTRAINT users_pkey;
       public            postgres    false    201            Y           2606    32842    hotel_features hotel_id    FK CONSTRAINT     �   ALTER TABLE ONLY public.hotel_features
    ADD CONSTRAINT hotel_id FOREIGN KEY (hotel_id) REFERENCES public.hotel(hotel_id) NOT VALID;
 A   ALTER TABLE ONLY public.hotel_features DROP CONSTRAINT hotel_id;
       public          postgres    false    204    2892    200            Z           2606    32876 #   pension pension_types_hotel_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.pension
    ADD CONSTRAINT pension_types_hotel_id_fkey FOREIGN KEY (hotel_id) REFERENCES public.hotel(hotel_id);
 M   ALTER TABLE ONLY public.pension DROP CONSTRAINT pension_types_hotel_id_fkey;
       public          postgres    false    200    2892    206            [           2606    32892    period period_hotel_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.period
    ADD CONSTRAINT period_hotel_id_fkey FOREIGN KEY (hotel_id) REFERENCES public.hotel(hotel_id);
 E   ALTER TABLE ONLY public.period DROP CONSTRAINT period_hotel_id_fkey;
       public          postgres    false    2892    200    208            ^           2606    32923 %   reservation reservation_hotel_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.reservation
    ADD CONSTRAINT reservation_hotel_id_fkey FOREIGN KEY (hotel_id) REFERENCES public.hotel(hotel_id);
 O   ALTER TABLE ONLY public.reservation DROP CONSTRAINT reservation_hotel_id_fkey;
       public          postgres    false    211    2892    200            ]           2606    32918 $   reservation reservation_room_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.reservation
    ADD CONSTRAINT reservation_room_id_fkey FOREIGN KEY (room_id) REFERENCES public.room(id);
 N   ALTER TABLE ONLY public.reservation DROP CONSTRAINT reservation_room_id_fkey;
       public          postgres    false    210    211    2902            \           2606    32905    room room_hotelid_fkey    FK CONSTRAINT     |   ALTER TABLE ONLY public.room
    ADD CONSTRAINT room_hotelid_fkey FOREIGN KEY (hotel_id) REFERENCES public.hotel(hotel_id);
 @   ALTER TABLE ONLY public.room DROP CONSTRAINT room_hotelid_fkey;
       public          postgres    false    2892    200    210            �   �   x�M�Mn�0��ϧ�	��ٚ�VH��n�0Q&62F��@��3p��`%1����F�ޛ�,����y��W�5��/��0ٹ:�9*Uc�Hp����$V�F"	��QF�IH%��1Uy��klȖ.���	?_t�A�g����ʱ���f�a�'Kx��?vÝMB��>O�=p�&�/�S �~<qt�������t���9V�UY�*�&J�_�Sm9      �   ;   x�3�L-ƅ��8��� �`�d���34�,)*M�i�9�0]���E/����� ��9	      �      x�3�4�,�4�2�4���=... �J�      �   0   x�3�4�4202�5 "C����2�4��"d���˔��L� D@�      �   �   x�]�M
�@��oN�����wU����-v(�n�2���+w�`N[Dὐ|IB�������j?�+��QM�r�ɩs�\rh�i,>��T���=��U6ڏ���;hhPW������6�?/�)Az'�(�;��Ѿ� f�P��m��9�(�ƾi�YL�Xh+?�c_OX����B��_�߫8��� �J8      �   T  x���]K�0��O~�HҦ���S���PA(�ڰb�B�*�{�4�Ң"	Z���8(l���|U<n6k��ž�]	�u��b��c ̟fS
�^
"@`�˞�kD����l��Ic���3�d�� ,F��p�����\�w=+ҟ v!ͅ��u���!%�K�:"l�؅b �$�}U�)�}W�w�{�Eы&s����5	��f��^�ȥp�>�36���K6
%:��x1�׏�|c��SZ����F~ͻ��hlMaW�	(5�K���^ƕ�@F�6�С=����CW��:�Ʀ�� ���D�7��2��R�=����1��6gQE8�n��!�`�۫      �   ;   x�3�LL���3�4426����9Ssr�+SS��0.�D�P��.i�i�P���� �k�     