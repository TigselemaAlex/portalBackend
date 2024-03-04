package com.example.portalbackend.service.generator;

import com.example.portalbackend.api.dto.request.passage.PassageCreateData;
import com.example.portalbackend.api.dto.request.residence.ResidenceCreateData;
import com.example.portalbackend.api.dto.request.residence.ResidenceUpdateData;
import com.example.portalbackend.api.dto.request.user.UserCreateData;
import com.example.portalbackend.domain.entity.Passage;
import com.example.portalbackend.domain.entity.Role;
import com.example.portalbackend.domain.entity.User;
import com.example.portalbackend.domain.repository.PassageRepository;
import com.example.portalbackend.domain.repository.RoleRepository;
import com.example.portalbackend.service.impl.PassageService;
import com.example.portalbackend.service.impl.ResidenceService;
import com.example.portalbackend.service.impl.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DataGeneratorService {

    private final RoleRepository roleRepository;
    private final UserService userService;
    private final PassageService passageService;
    private final ResidenceService residenceService;

    public DataGeneratorService(RoleRepository roleRepository, UserService userService, PassageService passageService, ResidenceService residenceService) {
        this.roleRepository = roleRepository;
        this.userService = userService;
        this.passageService = passageService;
        this.residenceService = residenceService;
    }

    public void generateRoles() {
        System.out.println("Generating roles");
        List<Role> roles = List.of(
                Role.builder().name("ADMIN").description("Administrador del sistema").build(),
                Role.builder().name("PRESIDENT").description("Presidente del conjunto habitacional").build(),
                Role.builder().name("VICE_PRESIDENT").description("Vicepresidente del conjunto habitacional").build(),
                Role.builder().name("SECRETARY").description("Secretario del conjunto habitacional").build(),
                Role.builder().name("TREASURER").description("Tesorero del conjunto habitacional").build(),
                Role.builder().name("OWNER").description("Propietario del conjunto habitacional").build(),
                Role.builder().name("TENANT").description("Inquilino del conjunto habitacional").build()

        );
        roleRepository.saveAll(roles);
    }

    public void generateUsers() {
        System.out.println("Generating users");
        List<UserCreateData> users = List.of(
            new UserCreateData(
                    "Alex Ignacio",
                    "Tigselema Pacheco",
                    "alextp.dev@gmail.com",
                    "0967205537",
                    "1803834371",
                    List.of(1L,2L,3L,4L,5L,6L,7L)),
            new UserCreateData(
                    "Ignacio",
                    "Peralvo Carvajal",
                    "nacho1ipc@gmail.com",
                    "0987564147",
                    "1710585231",
                    List.of(6L)),
            new UserCreateData(
                    "Cristian",
                    "Castillo",
                    "khris84k@hotmail.com",
                    "0984925295",
                    "1803762903",
                    List.of(6L)),
            new UserCreateData(
                    "Holger Gabriel",
                    "Valdez Chamorro",
                    "holgervaldez1982@hotmail.com",
                    "0984847381",
                    "1803480712",
                    List.of(6L)),
            new UserCreateData(
                    "Jose Luis",
                    "Jurado Campos",
                    "joseluisjuradocampos@hotmail.com",
                    "0983062603",
                    "0801714866",
                    List.of(6L)),
            new UserCreateData(
                    "Juan Javier",
                    "Chanalata Valle",
                    "jchanalata1@gmail.com",
                    "0999606921",
                    "1803076247",
                    List.of(6L)),
            new UserCreateData(
                    "Lizandro Paúl",
                    "Castillo Pérez",
                    "lpaulcastillo@gmail.com",
                    "0984755340",
                    "1803773207",
                    List.of(7L)),
            new UserCreateData(
                    "Guadalupe",
                    "Ortiz",
                    "gabys150587@gmail.com",
                    "0983875314",
                    "1804146098",
                    List.of(6L)),
            new UserCreateData(
                    "Margoth Alexandra",
                    "Castillo Ortiz",
                    "alexacastilloor@gmail.com",
                    "099610714",
                    "1802725018",
                    List.of(6L)),
            new UserCreateData(
                    "Daniel",
                    "Poveda Guaigua",
                    "dspoveda@hotmail.com",
                    "0999414415",
                    "1803279460",
                    List.of(6L)),
            new UserCreateData(
                    "Elina Maribel",
                    "Villagomez Vargas",
                    "eeduardoguevara@gmail.com",
                    "0999796572",
                    "1803125093",
                    List.of(6L)),
            new UserCreateData(
                    "Edgar Fabián",
                    "Gavilanes Montenegro",
                    "fabiangavilanes@hotmail.es",
                    "0939024426",
                    "1801389758",
                    List.of(6L)),
            new UserCreateData(
                    "Luis Raúl",
                    "Analuisa Pazmiño",
                    "luis27_anchaluisa@yahoo.com",
                    "0995629408",
                    "1801027564",
                    List.of(6L)),
            new UserCreateData(
                    "Tannia",
                    "Villegas",
                    "tan.ybonita@hotmail.com",
                    "0984394583",
                    "1803624434",
                    List.of(6L)),
            new UserCreateData(
                    "Ximena Elizabeth",
                    "Castro Miranda",
                    "ximedu@hotmail.com",
                    "0992932377",
                    "1803213121",
                    List.of(6L)),
            new UserCreateData(
                    "Cesar Gabriel",
                    "Gutierrez Murgueytio",
                    "cesargutierrez181@hotmail.com",
                    "0999847104",
                    "1802942951",
                    List.of(6L)),
            new UserCreateData(
                    "Vilma Marlene",
                    "Sanchez Castro",
                    "vilmasanchez64@hotmail.com",
                    "0962341964",
                    "1801954916",
                    List.of(6L)),
            new UserCreateData(
                    "Soledad Nathalie",
                    "Pascal Ortiz",
                    "Soledadnpascalo@gmail.com",
                    "0981046704",
                    "1712259496",
                    List.of(7L)),
            new UserCreateData(
                    "Carlos Gustavo",
                    "Tapia Córdova",
                    "gustav2012tapiac@gmail.com",
                    "0983508834",
                    "1803102654",
                    List.of(6L)),
            new UserCreateData(
                    "José Luis",
                    "Banda Banda",
                    "bandajoseluis@yahoo.es",
                    "0984751375",
                    "0502666852",
                    List.of(6L)),
            new UserCreateData(
                    "Jeffeson Omar",
                    "Armas Altamirano",
                    "omararmas40@gmail.com",
                    "0983841005",
                    "1804347951",
                    List.of(6L)),
            new UserCreateData(
                    "Martha Cecilia",
                    "Díaz Pulluquinga",
                    "marti.diaz.80@live.com",
                    "0998295883",
                    "1801916725",
                    List.of(6L)),
            new UserCreateData(
                    "Ignacio Gonzalo",
                    "Portero Cunlata",
                    "ignacioportero75@gmail.com",
                    "",
                    "1802936474",
                    List.of(6L)),
            new UserCreateData(
                    "Wigberto",
                    "Sanchez Peña",
                    "wigsoft45@gmail.com",
                    "0998384402",
                    "1803184710",
                    List.of(6L)),
            new UserCreateData(
                    "Juan Pablo",
                    "Freire Arias",
                    "juanfre_1976@hotmail.com",
                    "0992747295",
                    "1802823490",
                    List.of(6L)),
            new UserCreateData(
                    "Jose Luis",
                    "Flores Garcés",
                    "marcelog67@hotmail.com",
                    "0987974372",
                    "1802658441",
                    List.of(6L)),
            new UserCreateData(
                    "Marcelo",
                    "Caceres",
                    "marcelog67@hotmail.com",
                    "0998431945",
                    "1801725142",
                    List.of(6L)),
            new UserCreateData(
                    "Marcelo",
                    "Torres",
                    "marce_yerovi@hotmail.com",
                    "0992807739",
                    "1804113346",
                    List.of(7L)),
            new UserCreateData(
                    "Logia Soraya",
                    "Valle Robalindo",
                    "sori_isna@hotmail.com",
                    "0992656678",
                    "1802949790",
                    List.of(6L)),
            new UserCreateData(
                    "Judith Esmeralda",
                    "Fiallos Sánchez",
                    "esmeraldafiallos@gmail.com",
                    "0998442136",
                    "1801398247",
                    List.of(6L)),
            new UserCreateData(
                    "Maria Gabriela",
                    "Diaz Rodríguez",
                    "gaby.zaid@hotmail.com",
                    "0997930819",
                    "1803467172",
                    List.of(6L)),
            new UserCreateData(
                    "Mercedes",
                    "Guevara Paredes",
                    "gatita.guevara@gmail.com",
                    "0985734315",
                    "1802396901",
                    List.of(6L)),
            new UserCreateData(
                    "Jimena del Rocio",
                    "Ortuño Panoluisa",
                    "jimekda7@yahoo.es",
                    "0958764070",
                    "1803122009",
                    List.of(6L)),
            new UserCreateData(
                    "Terecita Emitelia",
                    "Salazar Sanchez",
                    "terecita.salazasanchez@gmail.com",
                    "0984640903",
                    "1801356195",
                    List.of(6L)),
            new UserCreateData(
                    "Luis Alfredo",
                    "Sánchez Sánchez",
                    "luis_sanches_x3@hotmail.com",
                    "0969507328",
                    "1803838091",
                    List.of(6L)),
            new UserCreateData(
                    "María Mireya",
                    "Moreno Paredes",
                    "mire_tebo2711@yahoo.com",
                    "",
                    "1720329059",
                    List.of(6L)),
            new UserCreateData(
                    "Johan Roberto",
                    "Romero Córdova",
                    "jjohanromero@hotmail.com",
                    "0998693267",
                    "0502126444",
                    List.of(6L)),
            new UserCreateData(
                    "Anibal Ruperto",
                    "Perez Perez",
                    "anibal_perez90@yahoo.com",
                    "0994917003",
                    "1802652808",
                    List.of(6L)),
            new UserCreateData(
                    "Maria Fernanda",
                    "Ramos Vargas",
                    "fernandaramosv16@gmail.com",
                    "0987360487",
                    "1803384799",
                    List.of(6L)),
            new UserCreateData(
                    "Edwin Javier",
                    "Zamora Zamora",
                    "zamorajz1997@gmail.com",
                    "0988524853",
                    "1805342571",
                    List.of(6L)),
            new UserCreateData(
                    "Fredy Miguel",
                    "Yanez Camacho",
                    "fedeyanez82@gmail.com",
                    "0981253581",
                    "0201568227",
                    List.of(6L)),
            new UserCreateData(
                    "Margoth",
                    "Villa",
                    "andres.alexander95@gmail.com",
                    "0980256919",
                    "0501076194",
                    List.of(6L)),
            new UserCreateData(
                    "Gabriela",
                    "Pico",
                    "gcpn1009@yahoo.es",
                    "0993737072",
                    "1803371045",
                    List.of(7L)),
            new UserCreateData(
                    "José Manuel",
                    "Vargas Álvarez",
                    "jochymanuel@gmail.com",
                    "0992576748",
                    "1801965045",
                    List.of(6L)),
            new UserCreateData(
                    "Mary Jacqueline",
                    "Grefa Tanguila",
                    "jacque_01@outlook.com",
                    "0990633345",
                    "1805067186",
                    List.of(6L)),
            new UserCreateData(
                    "José",
                    "Migues",
                    "caleroveronica91@yahoo.es",
                    "0987707005",
                    "1804765152",
                    List.of(7L)),
            new UserCreateData(
                    "Lilian Patricia",
                    "Cordova Altamirano",
                    "lilian011@gamail.com",
                    "0992964500",
                    "1802239630",
                    List.of(6L)),
            new UserCreateData(
                    "Elizabeth",
                    "Villalva Borja",
                    "elyvillalva@hotmail.com",
                    "0987301245",
                    "1802639201",
                    List.of(6L)),
            new UserCreateData(
                    "Daniel",
                    "Andrade",
                    "daniel2400416@gmail.com",
                    "0992639206",
                    "1804898334",
                    List.of(6L)),
            new UserCreateData(
                    "Marcelo",
                    "Villegas",
                    "flaklop@hotmail.com",
                    "0995862757",
                    "1803161288",
                    List.of(6L)),
            new UserCreateData(
                    "Jorge Enrique",
                    "Jordán Vaca",
                    "jejordan1977@yahoo.com",
                    "0998009525",
                    "1803079761",
                    List.of(6L)),
            new UserCreateData(
                    "Anibal Geovanny",
                    "Paredes Cabezas",
                    "geovaparedes@gmail.com",
                    "0999047561",
                    "1802303568",
                    List.of(6L)),
            new UserCreateData(
                    "Mariela",
                    "Malan",
                    "mari3lit4@gmail.com",
                    "",
                    "0501647689",
                    List.of(6L)),
            new UserCreateData(
                    "Germania Lorena",
                    "López Arroba",
                    "gnenalopez@gmail.com",
                    "0998549582",
                    "1802724995",
                    List.of(6L)),
            new UserCreateData(
                    "Susana",
                    "Bedon",
                    "tenaz_centauro@hotmail.com",
                    "0981625961",
                    "1802614709",
                    List.of(6L)),
            new UserCreateData(
                    "Lourdes Maricela",
                    "Bustos Calero",
                    "lourdesbustos470@gmail.com",
                    "0988564921",
                    "1804078077",
                    List.of(6L)),
            new UserCreateData(
                    "Andrea",
                    "Aular",
                    "andreaaular3000@mail.com",
                    "0983741685",
                    "1760993574",
                    List.of(7L)),
            new UserCreateData(
                    "Gladys Margot",
                    "Ramos López",
                    "gladys.ramos55ec@gmail.com",
                    "+34662084551",
                    "1801154680",
                    List.of(6L)),
            new UserCreateData(
                    "Isabel Cristina",
                    "Ramos López",
                    "criss.ramos26@gmail.com",
                    "+34652914543",
                    "1803042603",
                    List.of(6L)),
            new UserCreateData(
                    "Marshory",
                    "Chasi",
                    "tifaronwil@outlook.com",
                    "0983402282",
                    "1720579554",
                    List.of(6L)),
            new UserCreateData(
                    "Fausto Geovanny",
                    "Morales Solano",
                    "fgmorales097@gmail.com",
                    "0987822697",
                    "1804411773",
                    List.of(6L)),
            new UserCreateData(
                    "María Fernanda",
                    "Benavides Quesada",
                    "mafer_b7@yahoo.es",
                    "0980309656",
                    "1803136793",
                    List.of(6L)),
            new UserCreateData(
                    "Erika",
                    "Morales",
                    "noelia130810@gmail.com",
                    "0990237243",
                    "1803010329",
                    List.of(6L)),
            new UserCreateData(
                    "Renato",
                    "Almache",
                    "ralmache77@gmail.com",
                    "032445889",
                    "1803063047",
                    List.of(6L)),
            new UserCreateData(
                    "Jorge Luis",
                    "Maizanche",
                    "jluis182008@hotmail.com",
                    "0998654142",
                    "1804223186",
                    List.of(6L)),
            new UserCreateData(
                    "Christian",
                    "Merino",
                    "bettyale@hotmail.es",
                    "0983230818",
                    "1803324381",
                    List.of(6L)),
            new UserCreateData(
                    "Mercedes Alicia",
                    "Urquizo Díaz",
                    "a_urquizo@hotmail.com",
                    "0995663741",
                    "1802111359",
                    List.of(6L)),
            new UserCreateData(
                    "Mónica",
                    "Verdezoto",
                    "monaverdezoto@hotmail.com",
                    "0984338085",
                    "0201337094",
                    List.of(6L)),
            new UserCreateData(
                    "Gissela Alexandra",
                    "Cevallos Portero",
                    "alexandracevallosp@gmail.com",
                    "0998624822",
                    "1803386323",
                    List.of(6L)),
            new UserCreateData(
                    "Estefania Carolina",
                    "Bravo Sánchez",
                    "stefannia9318@gmail.com",
                    "0998131687",
                    "1804335709",
                    List.of(6L)),
            new UserCreateData(
                    "Carmena Graciela",
                    "Cuzco Paredes",
                    "gabriel22pc@gmail.com",
                    "0998879594",
                    "0501100515",
                    List.of(6L)),
            new UserCreateData(
                    "Sandra",
                    "Córdova Solórzano",
                    "sancordova@hotmail.com",
                    "0995795633",
                    "1103010011",
                    List.of(6L)),
            new UserCreateData(
                    "Ana Karina",
                    "Chica Miranda",
                    "kchicamiranda@gmail.com",
                    "0984528642",
                    "1500378052",
                    List.of(6L)),
            new UserCreateData(
                    "Patricio",
                    "Campaña",
                    "ginabarreroc@yahoo.es",
                    "2445996",
                    "1802266054",
                    List.of(6L)),
            new UserCreateData(
                    "Roberto",
                    "Guzmán ",
                    "robertogumaro@hotmail.com",
                    "0993799739",
                    "1103344907",
                    List.of(7L)),
            new UserCreateData(
                    "William Fernando ",
                    "Toapanta Flores",
                    "wtoapanta_75@hotmail.com",
                    "0984962237",
                    "1802901841",
                    List.of(6L)),
            new UserCreateData(
                    "Edison Geovanny ",
                    " Benalcázar Zambrano",
                    "eddy9631@hotmail.com",
                    "0980198492",
                    "2300327505",
                    List.of(7L)),
            new UserCreateData(
                    "Edith Narcisa ",
                    "Pérez Zabala ",
                    "edithperez7729@hotmail.com",
                    "0986593013",
                    "1712882909",
                    List.of(6L)),
            new UserCreateData(
                    "Kati del Consuelo ",
                    "Barrionuevo Velastegui",
                    "denisenunez2400@gmail.com",
                    "0984610281",
                    "1802758837",
                    List.of(6L)),
            new UserCreateData(
                    "Norma Liluana",
                    "Garcia Villa",
                    "nolygav@hotmail.com",
                    "0999202826",
                    "1802925261",
                    List.of(6L)),
            new UserCreateData(
                    "JACQUELINE IBETH ",
                    "ACARO",
                    "jakitty26@gmail.com",
                    "0999941779",
                    "1803533106",
                    List.of(6L)),
            new UserCreateData(
                    "Santiago Israel",
                    "Looez Sanchez ",
                    "sarahilopez4227@gmail.com",
                    "0969016563",
                    "1802988798",
                    List.of(7L)),
                new UserCreateData(
                        "Elizabeth Fernanda ",
                        "Freire Andagoya",
                        "fernanda_freire@hotmail.es",
                        "0991918005",
                        "1803690591",
                        List.of(6L)),
                new UserCreateData(
                        "MARCO VINICIO ",
                        "CHIRIBOGA COCA",
                        "mvchiriboga@hotmail.com",
                        "0984813982",
                        "1802282754",
                        List.of(6L)),
                new UserCreateData(
                        "Janeth",
                        "Martinez Sanchez",
                        "janemartinez0477@gmail.com",
                        "0998996134",
                        "1802229987",
                        List.of(6L)),
                new UserCreateData(
                        "Gladys Carmita",
                        "Barrera Guerrero ",
                        "anipattamys12@gmail.com",
                        "0987918579",
                        "1802178838",
                        List.of(7L)),
                new UserCreateData(
                        "Mariela",
                        "Japa Pico",
                        "marielajapa77@gmail.com",
                        "0995797877",
                        "1802935922",
                        List.of(7L)),
                new UserCreateData(
                        "Luis Carlos",
                        "Ulloa Pilliza",
                        "luiscarlosulloa-1986@hotmail.com",
                        "0995028426",
                        "1804155628",
                        List.of(7L)),
                new UserCreateData(
                        "Jenny Gabriela",
                        "Andagoya Nieto",
                        "andagoyagabriela0@gmail.com",
                        "0988131277",
                        "1205237298",
                        List.of(7L)),
                new UserCreateData(
                        "Paul Andersson",
                        "González Díaz",
                        "seguridadevitel@hotmail.com",
                        "0984358121",
                        "1802617124",
                        List.of(6L)),
                new UserCreateData(
                        "Teresa De Jesús",
                        "Merino Chávez",
                        "venuzthere@yahoo.com",
                        "0998100865",
                        "1600256653",
                        List.of(5L,6L)),
                new UserCreateData(
                        "Alexandra",
                        "Chávez",
                        "malexa1621@gmail.com",
                        "0991361651",
                        "1802623601",
                        List.of(7L)),
                new UserCreateData(
                        "Jennyfer Viviana",
                        "Benítez Huaca",
                        "johana_noe@hotmail.com",
                        "0987545724",
                        "0920207537",
                        List.of(7L)),
                new UserCreateData(
                        "Maria Fernanda",
                        "Guevara Sifuentes",
                        "mg8988532@gmail.com",
                        "0998239620",
                        "1803613577",
                        List.of(7L)),
                new UserCreateData(
                        "Ricardo Javier",
                        "Cevallos Lemus",
                        "wilmasaa@yahoo.com",
                        "0998854590",
                        "1802877603",
                        List.of(6L)),
                new UserCreateData(
                        "Mónica Lorena",
                        "Vela Tubon",
                        "lorenitavela116@gmail.com",
                        "0990434592",
                        "1802892321",
                        List.of(6L)),
                new UserCreateData(
                        "Henry",
                        "Rodriguez",
                        "henryrodriguez2006@hotmail.com",
                        "0998793526",
                        "1803295292",
                        List.of(6L)),
                new UserCreateData(
                        "María Dolores",
                        "Rojas Ortiz",
                        "lolirojas_@hotmail.com",
                        "0995826543",
                        "0103619839",
                        List.of(6L)),
                new UserCreateData(
                        "Nora",
                        "Landa",
                        "nlanda79@hotmail.com",
                        "0999237496",
                        "1600366494",
                        List.of(6L)),
                new UserCreateData(
                        "Norma Yolanda",
                        "Carrasco",
                        "nycarrasco2012@gmail.com",
                        "0995761834",
                        "2802911527",
                        List.of(6L)),
                new UserCreateData(
                        "Giorge Enrique",
                        "Rojas Bustamante",
                        "giorgerojas13@gmail.com",
                        "2445538",
                        "1102024021",
                        List.of(6L)),
                new UserCreateData(
                        "Juan",
                        "Vargas Espadas",
                        "jazbelita2020@gmail.com",
                        "0987184892",
                        "1600121691",
                        List.of(6L)),
                new UserCreateData(
                        "Lourdes Ingrit",
                        "Cisneros Pérez",
                        "lourdes15cisneros@hotmail.com",
                        "0993879552",
                        "1801880616",
                        List.of(6L)),
                new UserCreateData(
                        "Jorge Hermogenes",
                        "Morocho Vique",
                        "jorgemorocho73@hotmail.com",
                        "0992966467",
                        "0602748626",
                        List.of(6L)),
                new UserCreateData(
                        "Margarita del Consuelo",
                        "López Ramos",
                        "margaritalopez1974@gmail.com",
                        "0999271788",
                        "1802682748",
                        List.of(6L)),
                new UserCreateData(
                        "Jorge Patricio",
                        "Santamaría Cherrez",
                        "patricio_jorge@hotmail.es",
                        "0997979099",
                        "1804252078",
                        List.of(6L)),
                new UserCreateData(
                        "Carlos Elías",
                        "Gómez Luzuriaga",
                        "calingomez800@gmail.com",
                        "0981404517",
                        "1600190001",
                        List.of(6L)),
                new UserCreateData(
                        "Luis Alberto",
                        "Gavilanes Cordones",
                        "lizrthtipan@gmail.com",
                        "0995941580",
                        "1802912020",
                        List.of(7L)),
                new UserCreateData(
                        "Alejandra",
                        "Reyes",
                        "alebur_2010@hotmail.com",
                        "0984343621",
                        "1803421146",
                        List.of(7L)),
                new UserCreateData(
                        "María Victoria",
                        "Chauca Barrionuevo",
                        "mavic_1885@outlook.com",
                        "0958600173",
                        "1804310009",
                        List.of(7L)),
                new UserCreateData(
                        "Rodrigo",
                        "Freire",
                        "yogo102310@gmail.com",
                        "0992967993",
                        "1850330117",
                        List.of(6L)),
                new UserCreateData(
                        "Franklin",
                        "Moya",
                        "ker.valeria.moya@hotmail.com",
                        "0992639206",
                        "1801637792",
                        List.of(6L)),
                new UserCreateData(
                        "Johana Elizabeth",
                        "Perez Aguilar",
                        "johanitaelip37@gmail.com",
                        "0960192765",
                        "1803710761",
                        List.of(7L)),
                new UserCreateData(
                        "Lourdes",
                        "Santana Fiallos",
                        "lourdes-1801@hotmail.com",
                        "0992672395",
                        "1801775501",
                        List.of(6L)),
                new UserCreateData(
                        "Martha Cecilia",
                        "Veloz Zurita",
                        "armandoinfiniti@gmail.com",
                        "0998816059",
                        "1703781318",
                        List.of(6L)),
                new UserCreateData(
                        "Francisco Xavier",
                        "López Cabezas",
                        "franciscoxlopez759@gmail.com",
                        "0998482229",
                        "1804976510",
                        List.of(6L)),
                new UserCreateData(
                        "Christian",
                        "Velastegui",
                        "christianvelastegui01@gmail.com",
                        "0983707371",
                        "1804965208",
                        List.of(7L)),
                new UserCreateData(
                        "Julia del Rocío",
                        "Miranda Barros",
                        "juliamirandabarros105@gmail.com",
                        "0984838162",
                        "1802806156",
                        List.of(7L)),
                new UserCreateData(
                        "Hugo Rogelio",
                        "Altamirano Villarroel",
                        "hugorogel@hotmail.com",
                        "0998233472",
                        "1802650521",
                        List.of(6L)),
                new UserCreateData(
                        "Alfredo Mauricio",
                        "Herrera Andrade",
                        "halfredomauricio68@hotmail.com",
                        "032445924",
                        "1707873996",
                        List.of(6L)),
                new UserCreateData(
                        "Richard Nelson",
                        "Flores Ulloa",
                        "gatitaojeda51@gmail.com",
                        "0987131022",
                        "1802112167",
                        List.of(6L)),
                new UserCreateData(
                        "Alberto Jose",
                        "Altamirano Morantes",
                        "albertoaltamiranomorantes@gmail.com",
                        "0998040473",
                        "1803775798",
                        List.of(7L)),
                new UserCreateData(
                        "Ricardo",
                        "Oñate",
                        "seventen28@gmail.com",
                        "0969227776",
                        "1803472354",
                        List.of(6L)),
                new UserCreateData(
                        "Fredy",
                        "Osorio Castillo",
                        "freddyosorio514@gmail.com",
                        "445800",
                        "0201090800",
                        List.of(6L)),
                new UserCreateData(
                        "Ricardo Paul",
                        "Paredes Carrasco",
                        "rickytosky06@gmail.com",
                        "0998276858",
                        "1804099685",
                        List.of(6L)),
                new UserCreateData(
                        "Silvana Carola",
                        "González Díaz",
                        "silvana1232@gmail.com",
                        "0992527561",
                        "1802933513",
                        List.of(6L)),
                new UserCreateData(
                        "Sergio Darío",
                        "Morejón Hernández",
                        "sergiomorejon@hotmail.com",
                        "0999823703",
                        "0401219258",
                        List.of(6L)),
                new UserCreateData(
                        "Nelly Rosana",
                        "Bermeo Caicedo",
                        "nellybermeo75@hotmail.com",
                        "0992944396",
                        "1600302515",
                        List.of(6L)),
                new UserCreateData(
                        "Paul Alejandro",
                        "Cardenas Alvarez",
                        "cpaul3458@gmail.com",
                        "0983574434",
                        "1803747755",
                        List.of(6L)),
                new UserCreateData(
                        "Gabriela Alexandra",
                        "Sánchez Andrade",
                        "gabysanchezandrade@gmail.com",
                        "0998859495",
                        "1804107157",
                        List.of(6L)),
                new UserCreateData(
                        "Tyson",
                        "Rosas",
                        "tysonrosas77@gmail.com",
                        "0998245882",
                        "1803281516",
                        List.of(6L)),
                new UserCreateData(
                        "Leidy Lorena",
                        "López Gavilánez",
                        "dyloren85@gmail.com",
                        "0979202770",
                        "1205807835",
                        List.of(6L)),
                new UserCreateData(
                        "Edwin Nolberto",
                        "Herrera Lozada",
                        "sumilab1@hotmail.com",
                        "0999727449",
                        "0501255723",
                        List.of(6L)),
                new UserCreateData(
                        "Daniela",
                        "Galarza",
                        "dani.galarza240@gmail.com",
                        "0969076357",
                        "1804988333",
                        List.of(7L)),
                new UserCreateData(
                        "Franklin",
                        "Crespo",
                        "fr_crespo@hotmail.es",
                        "0979084504",
                        "1803619350",
                        List.of(7L)),
                new UserCreateData(
                        "Norma Alexandra",
                        "Gutiérrez Casco",
                        "norma_gutierrez76@yahoo.es",
                        "0999740588",
                        "0603046830",
                        List.of(6L)),
                new UserCreateData(
                        "Andrea",
                        "Acosta",
                        "acostamontesdesoca@gmail.com",
                        "0987815301",
                        "0503633372",
                        List.of(6L)),
                new UserCreateData(
                        "Maria Estela",
                        "Chuquina Tamaquiza",
                        "estela442000@yahoo.es",
                        "0998958441",
                        "1802519064",
                        List.of(6L)),
                new UserCreateData(
                        "Fausto Danilo",
                        "Guaigua Vizcaino",
                        "dguaiguavizcaino@yahoo.es",
                        "0979374258",
                        "1803721784",
                        List.of(6L)),
                new UserCreateData(
                        "Alvaro Daniel",
                        "Velastegui Altamirano",
                        "alvareinstegui@gmail.com",
                        "0995122516",
                        "1802988889",
                        List.of(6L)),
                new UserCreateData(
                        "Cinthya",
                        "Pesantez",
                        "cpesantez18@gmail.com",
                        "0997187775",
                        "1804024550",
                        List.of(7L)),
                new UserCreateData(
                        "Alejandra",
                        "Fuenmayor",
                        "moralesnancy432@gmail.com",
                        "0968892837",
                        "1804513644",
                        List.of(6L)),
                new UserCreateData(
                        "Jaime Ivan",
                        "Nuñez Bravo",
                        "jimmynunez@msn.com",
                        "0998621216",
                        "1704377561",
                        List.of(7L)),
                new UserCreateData(
                        "Ximena Janeth",
                        "Naranjo Rodríguez",
                        "borisubilluz64@gmail.com",
                        "0983544955",
                        "1600187593",
                        List.of(6L)),
                new UserCreateData(
                        "Angelica Jessenia",
                        "Ramirez Torrez",
                        "angiejessrt1978@gmail.com",
                        "0991103247",
                        "1204262024",
                        List.of(6L)),
                new UserCreateData(
                        "Clara Elena",
                        "Herrera Racines",
                        "nena.racines62@gmail.com",
                        "0997751743",
                        "1801703834",
                        List.of(6L)),
                new UserCreateData(
                        "Amanda Brigitte",
                        "Almeida Bajaña",
                        "amandabrigitte@gmail.com",
                        "0998871049",
                        "1600362758",
                        List.of(6L)),
                new UserCreateData(
                        "Ericka",
                        "Rada",
                        "Erickanakay20110750@gmail.com",
                        "0969010273",
                        "0250592839",
                        List.of(7L)),
                new UserCreateData(
                        "Marisol",
                        "Arroba Caceres",
                        "marroba69@icloud.com",
                        "0998531046",
                        "1802364677",
                        List.of(6L)),
                new UserCreateData(
                        "Hugo",
                        "Fiallos Rodríguez",
                        "fiallosrandy@gmail.com",
                        "0992979994",
                        "1500313190",
                        List.of(6L)),
                new UserCreateData(
                        "Luz Marina",
                        "Pérez Pérez",
                        "marinaperezp54@hotmail.com",
                        "0998588142",
                        "1801369453",
                        List.of(6L)),
                new UserCreateData(
                        "María del Pilar",
                        "Álvarez Aguilar",
                        "mapial77@hotmail.com",
                        "0992635393",
                        "1802849271",
                        List.of(6L)),
                new UserCreateData(
                        "Paulina Elisabeth",
                            "Díaz Rodriguez",
                        "ingzaide@hotmail.com",
                        "0990585540",
                        "1802979326",
                        List.of(6L)),
                new UserCreateData(
                        "Jessica Nataly",
                        "Tapia Ledesma",
                        "jessy.nicol.ddef@hotmail.com",
                        "0987905049",
                        "1804168290",
                        List.of(6L)),
                new UserCreateData(
                        "María Belén",
                        "Pérez Pérez",
                        "marybelen24@hotmail.com",
                        "0982518854",
                        "2000042057",
                        List.of(6L)),
                new UserCreateData(
                        "María Beatriz",
                        "Rueda Rodríguez",
                        "mabeatriz1970@hotmail.com",
                        "0999844934",
                        "1102875539",
                        List.of(6L)),
                new UserCreateData(
                        "Lorena Alexandra",
                        "Abril Lara",
                        "labril1417@gmail.com",
                        "0964090948",
                        "1804761417",
                        List.of(6L)),
                new UserCreateData(
                        "María Elena",
                        "Tamayo",
                        "cynthiaespin513@gmail.com",
                        "0992721396",
                        "1800508689",
                        List.of(6L)),
                new UserCreateData(
                        "Pablo Misael",
                        "Arias Candilejo",
                        "pabloarias1972@hotmail.com",
                        "0995120746",
                        "0201298130",
                        List.of(6L)),
                new UserCreateData(
                        "Gladys Fabiola",
                        "Zurita Ruano",
                        "fabys18@live.com",
                        "0979382440",
                        "1801081132",
                        List.of(6L)),
                new UserCreateData(
                        "Ana del Carmen",
                        "Martínez Moya",
                        "anitamartinezmoya28@gmail.com",
                        "0991631714",
                        "1801957422",
                        List.of(7L)),
                new UserCreateData(
                        "Alejandrina Otilia",
                        "Altamirano Campos",
                        "nicolekaren960705@gmail.com",
                        "0984892695",
                        "1708310436",
                        List.of(6L)),
                new UserCreateData(
                        "Marco Patricio",
                        "Hidalgo",
                        "patriciohidalgo1968@gmail.com",
                        "0986732402",
                        "1802271864",
                        List.of(6L)),
                new UserCreateData(
                        "Rosario Katherine",
                        "García Ramos",
                        "katherinegarciaramos@gmail.com",
                        "0984762834",
                        "1803551918",
                        List.of(6L)),
                new UserCreateData(
                        "Jaqueline",
                        "Cordova",
                        "jaquimjcs@hotmail.com",
                        "0995653104",
                        "1802006872",
                        List.of(6L)),
                new UserCreateData(
                        "Cristian Lizardo",
                        "Defaz Morales",
                        "defazcristian3@gmail.com",
                        "0990817536",
                        "0502917123",
                        List.of(2L,6L)),
                new UserCreateData(
                        "Leonor Esthela",
                        "Naranjo Llango",
                        "estela.naranjo081@gmail.com",
                        "0984058324",
                        "1801160951",
                        List.of(5L,6L)),
                new UserCreateData(
                        "Ana",
                        "Uñug",
                        "anapau7615@gmil.com",
                        "0979024550",
                        "1802938892",
                        List.of(6L)),
                new UserCreateData(
                        "David",
                        "Franco",
                        "david30neyy@gmail.com",
                        "0986613952",
                        "1802976974",
                        List.of(6L)),
                new UserCreateData(
                        "José Ricardo",
                        "Mena Arellano",
                        "ricky7853@yahoo.es",
                        "0998033237",
                        "1802904647",
                        List.of(6L)),
                new UserCreateData(
                        "Lenin Alfonso",
                        "Villacreses Tonato",
                        "villacresesi@yahoo.com",
                        "0984381757",
                        "1802370609",
                        List.of(6L)),
                new UserCreateData(
                        "Marcos Eduardo",
                        "Mejia Morales",
                        "maykmejiass@hotmail.com",
                        "0984979366",
                        "1801897826",
                        List.of(6L)),
                new UserCreateData(
                        "Germania Leonor",
                        "Vargas León",
                        "germaniavargasl@gmail.com",
                        "0983161572",
                        "1804632907",
                        List.of(6L)),
                new UserCreateData(
                        "Judit Leonor",
                        "Ramos Lescano",
                        "ramosjudit@yahoo.es",
                        "0981999784",
                        "1802537868",
                        List.of(6L)),
                new UserCreateData(
                        "Luis Alfredo",
                        "Díaz Flores",
                        "luisalfredo-df@hotmail.com",
                        "0992163809",
                        "1710364876",
                        List.of(6L))
            );

        users.forEach(userService::create);
    }

    public void generatePassages() {
        System.out.println("Generating passages");
        List<PassageCreateData> createData = List.of(
                new PassageCreateData("Calle Caracas"),
                new PassageCreateData("Pasaje Maracay"),
                new PassageCreateData("Calle Corrientes"),
                new PassageCreateData("Pasaje Osorno"),
                new PassageCreateData("Pasaje Ica"),
                new PassageCreateData("Puerto Principe"),
                new PassageCreateData("Calle Iquique"),
                new PassageCreateData("Pasaje Corrientes"),
                new PassageCreateData("Pasaje Rosario"),
                new PassageCreateData("Pasaje San Estanislao"),
                new PassageCreateData("Cale Tucumán"),
                new PassageCreateData("Calle Concepción")
        );
        createData.forEach(passageService::create);
    }

    public void generateResidences() {
        System.out.println("Generating residences");
        Passage calleCaracas = passageService.findByName("Calle Caracas");
        Passage pasajeMaracay = passageService.findByName("Pasaje Maracay");
        Passage calleCorrientes = passageService.findByName("Calle Corrientes");
        Passage pasajeOsorno = passageService.findByName("Pasaje Osorno");
        Passage pasajeIca = passageService.findByName("Pasaje Ica");
        Passage puertoPrincipe = passageService.findByName("Puerto Principe");
        Passage calleIquique = passageService.findByName("Calle Iquique");
        Passage pasajeCorrientes = passageService.findByName("Pasaje Corrientes");
        Passage pasajeRosario = passageService.findByName("Pasaje Rosario");
        Passage pasajeSanEstanislao = passageService.findByName("Pasaje San Estanislao");
        Passage calleTucuman = passageService.findByName("Cale Tucumán");
        Passage calleConcepcion = passageService.findByName("Calle Concepción");

        List<ResidenceCreateData> createData = new ArrayList<>();

        for (int i = 1; i <= 18; i++) {
            createData.add(new ResidenceCreateData(String.valueOf(i), null,calleCaracas.getId()));
        }
        for (int i = 19; i <= 53; i++) {
            createData.add(new ResidenceCreateData(String.valueOf(i), null,pasajeMaracay.getId()));
        }
        for (int i = 54; i <= 61; i++) {
            createData.add(new ResidenceCreateData(String.valueOf(i), null,calleCorrientes.getId()));
        }
        for (int i = 62; i <= 79; i++) {
            createData.add(new ResidenceCreateData(String.valueOf(i), null,pasajeOsorno.getId()));
        }
        for (int i = 80; i <= 97; i++) {
            createData.add(new ResidenceCreateData(String.valueOf(i), null,pasajeIca.getId()));
        }
        for (int i = 107; i <= 116; i++) {
            createData.add(new ResidenceCreateData(String.valueOf(i), null,pasajeIca.getId()));
        }
        for (int i = 98; i <= 106; i++) {
            createData.add(new ResidenceCreateData(String.valueOf(i), null,puertoPrincipe.getId()));
        }
        for (int i = 117; i <= 126; i++) {
            createData.add(new ResidenceCreateData(String.valueOf(i), null,puertoPrincipe.getId()));
        }
        for (int i = 127; i <= 153; i++) {
            createData.add(new ResidenceCreateData(String.valueOf(i), null,calleIquique.getId()));
        }
        for (int i = 154; i <= 183; i++) {
            createData.add(new ResidenceCreateData(String.valueOf(i), null,pasajeCorrientes.getId()));
        }
        for (int i = 184; i <= 216; i++) {
            createData.add(new ResidenceCreateData(String.valueOf(i), null,pasajeRosario.getId()));
        }
        for (int i = 217; i <= 253; i++) {
            createData.add(new ResidenceCreateData(String.valueOf(i), null,pasajeSanEstanislao.getId()));
        }
        for (int i = 254; i <= 288; i++) {
            createData.add(new ResidenceCreateData(String.valueOf(i), null,calleTucuman.getId()));
        }
        for (int i = 289; i <= 303; i++) {
            createData.add(new ResidenceCreateData(String.valueOf(i), null,calleConcepcion.getId()));
        }
        createData.forEach(residenceService::save);
    }

    public void assignUsersToResidences() {
        System.out.println("Assigning users to residences");
        residenceService.update(new ResidenceUpdateData(1L), 276L);
        residenceService.update(new ResidenceUpdateData(2L), 1L);
        residenceService.update(new ResidenceUpdateData(3L), 13L);
        residenceService.update(new ResidenceUpdateData(4L), 14L);
        residenceService.update(new ResidenceUpdateData(5L), 15L);
        residenceService.update(new ResidenceUpdateData(6L), 17L);
        residenceService.update(new ResidenceUpdateData(7L), 19L);
        residenceService.update(new ResidenceUpdateData(8L), 20L);
        //residenceService.update(new ResidenceUpdateData(9L), 21L);
        residenceService.update(new ResidenceUpdateData(10L), 22L);
        residenceService.update(new ResidenceUpdateData(11L), 27L);
        residenceService.update(new ResidenceUpdateData(12L), 31L);
        residenceService.update(new ResidenceUpdateData(13L), 39L);
        residenceService.update(new ResidenceUpdateData(14L), 40L);
        residenceService.update(new ResidenceUpdateData(15L), 41L);
        residenceService.update(new ResidenceUpdateData(16L), 43L);
        residenceService.update(new ResidenceUpdateData(17L), 50L);
        residenceService.update(new ResidenceUpdateData(18L), 51L);
        residenceService.update(new ResidenceUpdateData(19L), 57L);
        residenceService.update(new ResidenceUpdateData(20L), 60L);
        residenceService.update(new ResidenceUpdateData(21L), 62L);
        residenceService.update(new ResidenceUpdateData(22L), 69L);
        residenceService.update(new ResidenceUpdateData(23L), 70L);
        residenceService.update(new ResidenceUpdateData(24L), 72L);
        residenceService.update(new ResidenceUpdateData(25L), 73L);
        residenceService.update(new ResidenceUpdateData(26L), 75L);
        residenceService.update(new ResidenceUpdateData(27L), 76L);
        residenceService.update(new ResidenceUpdateData(28L), 79L);
        residenceService.update(new ResidenceUpdateData(29L), 82L);
        residenceService.update(new ResidenceUpdateData(30L), 85L);
        residenceService.update(new ResidenceUpdateData(31L), 86L);
        residenceService.update(new ResidenceUpdateData(32L), 89L);
        residenceService.update(new ResidenceUpdateData(33L), 91L);
        residenceService.update(new ResidenceUpdateData(34L), 92L);
        residenceService.update(new ResidenceUpdateData(35L), 93L);
        residenceService.update(new ResidenceUpdateData(36L), 94L);
        residenceService.update(new ResidenceUpdateData(37L), 95L);
        residenceService.update(new ResidenceUpdateData(38L), 96L);
        residenceService.update(new ResidenceUpdateData(39L), 97L);
        residenceService.update(new ResidenceUpdateData(40L), 98L);
        residenceService.update(new ResidenceUpdateData(41L), 100L);
        residenceService.update(new ResidenceUpdateData(42L), 102L);
        residenceService.update(new ResidenceUpdateData(43L), 103L);
        residenceService.update(new ResidenceUpdateData(44L), 106L);
        residenceService.update(new ResidenceUpdateData(45L), 108L);
        residenceService.update(new ResidenceUpdateData(46L), 110L);
        residenceService.update(new ResidenceUpdateData(47L), 114L);
        residenceService.update(new ResidenceUpdateData(48L), 115L);
        residenceService.update(new ResidenceUpdateData(49L), 116L);
        residenceService.update(new ResidenceUpdateData(50L), 119L);
        residenceService.update(new ResidenceUpdateData(51L), 121L);
        residenceService.update(new ResidenceUpdateData(52L), 122L);
        residenceService.update(new ResidenceUpdateData(53L), 125L);
        residenceService.update(new ResidenceUpdateData(54L), 126L);
        residenceService.update(new ResidenceUpdateData(55L), 128L);
        residenceService.update(new ResidenceUpdateData(56L), 129L);
        residenceService.update(new ResidenceUpdateData(57L), 130L);
        residenceService.update(new ResidenceUpdateData(58L), 131L);
        residenceService.update(new ResidenceUpdateData(59L), 132L);
        residenceService.update(new ResidenceUpdateData(60L), 135L);
        residenceService.update(new ResidenceUpdateData(61L), 138L);
        residenceService.update(new ResidenceUpdateData(62L), 140L);
        residenceService.update(new ResidenceUpdateData(63L), 141L);
        residenceService.update(new ResidenceUpdateData(64L), 142L);
        residenceService.update(new ResidenceUpdateData(65L), 144L);
        residenceService.update(new ResidenceUpdateData(66L), 145L);
        residenceService.update(new ResidenceUpdateData(67L), 147L);
        residenceService.update(new ResidenceUpdateData(68L), 149L);
        residenceService.update(new ResidenceUpdateData(69L), 157L);
        residenceService.update(new ResidenceUpdateData(70L), 158L);
        residenceService.update(new ResidenceUpdateData(71L), 160L);
        residenceService.update(new ResidenceUpdateData(72L), 161L);
        residenceService.update(new ResidenceUpdateData(73L), 164L);
        residenceService.update(new ResidenceUpdateData(74L), 167L);
        residenceService.update(new ResidenceUpdateData(75L), 168L);
        residenceService.update(new ResidenceUpdateData(76L), 172L);
        residenceService.update(new ResidenceUpdateData(77L), 175L);
        residenceService.update(new ResidenceUpdateData(78L), 176L);
        residenceService.update(new ResidenceUpdateData(79L), 177L);
        residenceService.update(new ResidenceUpdateData(80L), 178L);
        residenceService.update(new ResidenceUpdateData(81L), 184L);
        residenceService.update(new ResidenceUpdateData(82L), 186L);
        residenceService.update(new ResidenceUpdateData(83L), 187L);
        residenceService.update(new ResidenceUpdateData(84L), 191L);
        residenceService.update(new ResidenceUpdateData(85L), 192L);
        residenceService.update(new ResidenceUpdateData(86L), 193L);
        residenceService.update(new ResidenceUpdateData(87L), 196L);
        residenceService.update(new ResidenceUpdateData(88L), 197L);
        residenceService.update(new ResidenceUpdateData(89L), 199L);
        residenceService.update(new ResidenceUpdateData(90L), 202L);
        residenceService.update(new ResidenceUpdateData(91L), 205L);
        residenceService.update(new ResidenceUpdateData(92L), 206L);
        residenceService.update(new ResidenceUpdateData(93L), 207L);
        residenceService.update(new ResidenceUpdateData(94L), 209L);
        residenceService.update(new ResidenceUpdateData(95L), 211L);
        residenceService.update(new ResidenceUpdateData(96L), 214L);
        residenceService.update(new ResidenceUpdateData(97L), 217L);
        residenceService.update(new ResidenceUpdateData(98L), 218L);
        residenceService.update(new ResidenceUpdateData(99L), 221L);
        residenceService.update(new ResidenceUpdateData(100L), 222L);
        residenceService.update(new ResidenceUpdateData(101L), 223L);
        residenceService.update(new ResidenceUpdateData(102L), 230L);
        residenceService.update(new ResidenceUpdateData(103L), 231L);
        residenceService.update(new ResidenceUpdateData(104L), 232L);
        residenceService.update(new ResidenceUpdateData(105L), 233L);
        residenceService.update(new ResidenceUpdateData(106L), 234L);
        residenceService.update(new ResidenceUpdateData(107L), 237L);
        residenceService.update(new ResidenceUpdateData(108L), 240L);
        residenceService.update(new ResidenceUpdateData(109L), 241L);
        residenceService.update(new ResidenceUpdateData(110L), 242L);
        residenceService.update(new ResidenceUpdateData(111L), 243L);
        residenceService.update(new ResidenceUpdateData(112L), 248L);
        residenceService.update(new ResidenceUpdateData(113L), 249L);
        residenceService.update(new ResidenceUpdateData(114L), 251L);
        residenceService.update(new ResidenceUpdateData(115L), 252L);
        residenceService.update(new ResidenceUpdateData(116L), 254L);
        residenceService.update(new ResidenceUpdateData(117L), 257L);
        residenceService.update(new ResidenceUpdateData(118L), 258L);
        residenceService.update(new ResidenceUpdateData(119L), 261L);
        residenceService.update(new ResidenceUpdateData(120L), 263L);
        residenceService.update(new ResidenceUpdateData(121L), 265L);
        residenceService.update(new ResidenceUpdateData(122L), 266L);
        residenceService.update(new ResidenceUpdateData(123L), 267L);
        residenceService.update(new ResidenceUpdateData(124L), 269L);
        residenceService.update(new ResidenceUpdateData(125L), 274L);
        residenceService.update(new ResidenceUpdateData(126L), 275L);
        residenceService.update(new ResidenceUpdateData(127L), 277L);
        residenceService.update(new ResidenceUpdateData(128L), 278L);
        residenceService.update(new ResidenceUpdateData(129L), 281L);
        residenceService.update(new ResidenceUpdateData(130L), 284L);
        residenceService.update(new ResidenceUpdateData(131L), 285L);
        residenceService.update(new ResidenceUpdateData(132L), 286L);
        residenceService.update(new ResidenceUpdateData(133L), 287L);
        residenceService.update(new ResidenceUpdateData(134L), 288L);
        residenceService.update(new ResidenceUpdateData(135L), 289L);
        residenceService.update(new ResidenceUpdateData(136L), 290L);
        residenceService.update(new ResidenceUpdateData(137L), 291L);
        residenceService.update(new ResidenceUpdateData(138L), 292L);
        residenceService.update(new ResidenceUpdateData(139L), 294L);
        residenceService.update(new ResidenceUpdateData(140L), 297L);
        residenceService.update(new ResidenceUpdateData(141L), 299L);
        residenceService.update(new ResidenceUpdateData(142L), 300L);
        //residenceService.update(new ResidenceUpdateData(143L), 301L);
        residenceService.update(new ResidenceUpdateData(144L), 150L);
        residenceService.update(new ResidenceUpdateData(145L), 107L);
        residenceService.update(new ResidenceUpdateData(146L), 2L);
        residenceService.update(new ResidenceUpdateData(147L), 6L);
        residenceService.update(new ResidenceUpdateData(148L), 21L);
        residenceService.update(new ResidenceUpdateData(149L), 28L);
        residenceService.update(new ResidenceUpdateData(150L), 3L);
        residenceService.update(new ResidenceUpdateData(151L), 58L);
        residenceService.update(new ResidenceUpdateData(152L), 7L);
        residenceService.update(new ResidenceUpdateData(153L), 8L);
        residenceService.update(new ResidenceUpdateData(154L), 84L);
        residenceService.update(new ResidenceUpdateData(155L), 99L);
        residenceService.update(new ResidenceUpdateData(156L), 111L);
        residenceService.update(new ResidenceUpdateData(156L), 162L);
        residenceService.update(new ResidenceUpdateData(157L), 146L);
        residenceService.update(new ResidenceUpdateData(157L), 133L);
        residenceService.update(new ResidenceUpdateData(158L), 235L);
        residenceService.update(new ResidenceUpdateData(158L), 136L);
        residenceService.update(new ResidenceUpdateData(159L), 67L);
        residenceService.update(new ResidenceUpdateData(159L), 74L);
        residenceService.update(new ResidenceUpdateData(160L), 183L);
        residenceService.update(new ResidenceUpdateData(161L), 88L);
        residenceService.update(new ResidenceUpdateData(162L), 200L);
        residenceService.update(new ResidenceUpdateData(163L), 228L);
        residenceService.update(new ResidenceUpdateData(164L), 5L);
        residenceService.update(new ResidenceUpdateData(165L), 63L);
        residenceService.update(new ResidenceUpdateData(166L), 84L);
        residenceService.update(new ResidenceUpdateData(167L), 71L);
        residenceService.update(new ResidenceUpdateData(168L), 198L);
        residenceService.update(new ResidenceUpdateData(169L), 166L);
        residenceService.update(new ResidenceUpdateData(170L), 123L);
        residenceService.update(new ResidenceUpdateData(171L), 255L);
        residenceService.update(new ResidenceUpdateData(172L), 220L);
        residenceService.update(new ResidenceUpdateData(173L), 279L);
        residenceService.update(new ResidenceUpdateData(174L), 245L);
        residenceService.update(new ResidenceUpdateData(175L), 139L);

    }
}
