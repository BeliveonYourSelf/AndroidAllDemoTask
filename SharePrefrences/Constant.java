package com.wildbeest.airhorn.funnysound.audiotool.utils;

import androidx.recyclerview.widget.ItemTouchHelper;

import com.wildbeest.airhorn.funnysound.audiotool.R;
import com.wildbeest.airhorn.funnysound.audiotool.model.Audio;

import java.util.ArrayList;
import java.util.List;

public class Constant {
    public static final String SOUND = "sound";
    public static String position = "position";
    public static final String PASS_TITLE = "pass_title";
    public static int[] air_horn = {R.raw.air_horn_1, R.raw.air_horn_2, R.raw.air_horn_3, R.raw.air_horn_4, R.raw.air_horn_5, R.raw.air_horn_6, R.raw.air_horn_7, R.raw.air_horn_8, R.raw.air_horn_9};
    public static int[] burping = {R.raw.burping_1, R.raw.burping_2, R.raw.burping_3, R.raw.burping_4, R.raw.burping_5, R.raw.burping_6, R.raw.burping_7, R.raw.burping_8, R.raw.burping_9, R.raw.burping_10, R.raw.burping_11, R.raw.burping_12};
    public static int[] car_horn = {R.raw.car_horn_1, R.raw.car_horn_2, R.raw.car_horn_3, R.raw.car_horn_4, R.raw.car_horn_5};
    public static int[] door_bell = {R.raw.door_bell_1, R.raw.door_bell_2, R.raw.door_bell_3, R.raw.door_bell_4, R.raw.door_bell_5, R.raw.door_bell_6, R.raw.door_bell_7, R.raw.door_bell_8, R.raw.door_bell_9, R.raw.door_bell_10};
    public static int[] engine_sound = {R.raw.engine_1, R.raw.engine_2, R.raw.engine_3, R.raw.engine_4, R.raw.engine_5};
    public static int[] fart_sound = {R.raw.fart_1, R.raw.fart_2, R.raw.fart_3, R.raw.fart_4, R.raw.fart_5, R.raw.fart_6, R.raw.fart_7, R.raw.fart_8, R.raw.fart_9, R.raw.fart_10, R.raw.fart_11, R.raw.fart_12, R.raw.fart_13, R.raw.fart_14, R.raw.fart_15};
    public static int[] fart_symphony = {R.raw.fart_symphony_1, R.raw.fart_symphony_2, R.raw.fart_symphony_3, R.raw.fart_symphony_4, R.raw.fart_symphony_5, R.raw.fart_symphony_6, R.raw.fart_symphony_7, R.raw.fart_symphony_8, R.raw.fart_symphony_9, R.raw.fart_symphony_10, R.raw.fart_symphony_11, R.raw.fart_symphony_12};
    public static int[] gun = {R.raw.gun_1, R.raw.gun_2, R.raw.gun_3, R.raw.gun_4, R.raw.gun_5, R.raw.gun_6, R.raw.gun_7, R.raw.gun_8, R.raw.gun_9, R.raw.gun_10, R.raw.gun_11, R.raw.gun_12, R.raw.gun_13, R.raw.gun_14};
    public static int[] hair_clipper = {R.raw.hair_clipper_1, R.raw.hair_clipper_2, R.raw.hair_clipper_3, R.raw.hair_clipper_4, R.raw.hair_clipper_5, R.raw.hair_clipper_6, R.raw.hair_clipper_7};
    public static int[] laugh_and_clap = {R.raw.laugh_and_clap_1, R.raw.laugh_and_clap_2, R.raw.laugh_and_clap_3, R.raw.laugh_and_clap_4, R.raw.laugh_and_clap_5, R.raw.laugh_and_clap_6, R.raw.laugh_and_clap_7, R.raw.laugh_and_clap_8, R.raw.laugh_and_clap_9, R.raw.laugh_and_clap_10};
    public static int[] man_cough = {R.raw.man_cough_1, R.raw.man_cough_2, R.raw.man_cough_3, R.raw.man_cough_4, R.raw.man_cough_5, R.raw.man_cough_6, R.raw.man_cough_7, R.raw.man_cough_8, R.raw.man_cough_9, R.raw.man_cough_10, R.raw.man_cough_11, R.raw.man_cough_12, R.raw.man_cough_13, R.raw.man_cough_14};
    public static int[] man_sneeze = {R.raw.man_sneeze_1, R.raw.man_sneeze_2, R.raw.man_sneeze_3, R.raw.man_sneeze_4, R.raw.man_sneeze_5, R.raw.man_sneeze_6, R.raw.man_sneeze_7};
    public static int[] police_siren = {R.raw.police_siren_1, R.raw.police_siren_2, R.raw.police_siren_3, R.raw.police_siren_4, R.raw.police_siren_5, R.raw.police_siren_6};
    public static int[] scary = {R.raw.scary_1, R.raw.scary_2, R.raw.scary_3, R.raw.scary_4, R.raw.scary_5, R.raw.scary_6, R.raw.scary_7, R.raw.scary_8, R.raw.scary_9, R.raw.scary_10,};
    public static int[] toilet_flushing = {R.raw.toilet_flushing_1, R.raw.toilet_flushing_2, R.raw.toilet_flushing_3, R.raw.toilet_flushing_4, R.raw.toilet_flushing_5, R.raw.toilet_flushing_6, R.raw.toilet_flushing_7, R.raw.toilet_flushing_8};
    public static int[] white_noises = {R.raw.white_noise_1, R.raw.white_noise_2, R.raw.white_noise_3};
    public static int[] woman_cough = {R.raw.woman_cough_1, R.raw.woman_cough_2, R.raw.woman_cough_3, R.raw.woman_cough_4, R.raw.woman_cough_5, R.raw.woman_cough_6, R.raw.woman_cough_7, R.raw.woman_cough_8, R.raw.woman_cough_9, R.raw.woman_cough_10, R.raw.woman_cough_11, R.raw.woman_cough_12};
    public static int[] woman_sneeze = {R.raw.woman_sneeze_1, R.raw.woman_sneeze_2, R.raw.woman_sneeze_3, R.raw.woman_sneeze_4, R.raw.woman_sneeze_5, R.raw.woman_sneeze_6, R.raw.woman_sneeze_7, R.raw.woman_sneeze_8, R.raw.woman_sneeze_9, R.raw.woman_sneeze_10, R.raw.woman_sneeze_11, R.raw.woman_sneeze_12};
    public static final String AUDIO_NAME = "AUDIO_NAME";
    public static final String EFFECTS = "effects";
    public static final String INDEX_IMAGE = "indexImage";
    public static final String MUSIC_FILE = "Music_File";
    public static Audio audio;
    public static List<Audio> MergeAudioList;
    public static int VIBRATOR = ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION;
    public static final int[] imgGlassIDs = {R.drawable.ic_glass_1, R.drawable.ic_glass_2, R.drawable.ic_glass_3, R.drawable.ic_glass_4};
    public static final int[] imgCurrent = {R.drawable.ic_electric, R.drawable.ic_shok, R.drawable.ic_current, R.drawable.ic_current_2};
    public static final int[] imgFire = {R.drawable.ic_fire_1, R.drawable.ic_fire_2, R.drawable.ic_fire_3, R.drawable.ic_fire_4, R.drawable.ic_fire_5};
    public static final int[] thumpIDs = {R.drawable.brake1_thb, R.drawable.brake2_thb, R.drawable.brake3_thb, R.drawable.brake4_thb};

    public static ArrayList<Integer> IndianImages=new ArrayList<>();
    public static ArrayList<Integer> UsaImages=new ArrayList<>();
    public static ArrayList<Integer> AustraliaImages=new ArrayList<>();
    public static ArrayList<Integer> CanadaImages=new ArrayList<>();
    public static ArrayList<Integer> JapanImages=new ArrayList<>();
    public static ArrayList<Integer> RussiaImages=new ArrayList<>();
    public static ArrayList<Integer> PakistanImages=new ArrayList<>();
    public static ArrayList<Integer> UkImages=new ArrayList<>();
    public static ArrayList<Integer> MaxicoImages=new ArrayList<>();
    public static ArrayList<Integer> SpanishImages=new ArrayList<>();
    public static ArrayList<Integer> ChineseImages=new ArrayList<>();
    public static ArrayList<String> girlsList=new ArrayList<>();

    public static void AddIndianGirlsData() {
        girlsList.clear();
        girlsList.add("Chaitali");
        girlsList.add("Nini");
        girlsList.add("Usha");
        girlsList.add("Chumki");
        girlsList.add("Rasmi");
        girlsList.add("Susmita");
        girlsList.add("Roza");
        girlsList.add("Renu");
        girlsList.add("Shravya");
        girlsList.add("Saria");
        IndianImages.clear();
        IndianImages.add(R.drawable.ind0);
        IndianImages.add(R.drawable.ind2);
        IndianImages.add(R.drawable.ind3);
        IndianImages.add(R.drawable.ind4);
        IndianImages.add(R.drawable.ind5);
        IndianImages.add(R.drawable.ind6);
        IndianImages.add(R.drawable.ind7);
        IndianImages.add(R.drawable.ind8);
        IndianImages.add(R.drawable.ind9);
        IndianImages.add(R.drawable.ind10);

    }

    public static void AddUsaGirlsData() {
        girlsList.clear();
        girlsList.add("Sadie");
        girlsList.add("Thea");
        girlsList.add("Bonnie");
        girlsList.add("Paige");
        girlsList.add("Louise");
        girlsList.add("Alexia");
        girlsList.add("jenifer");
        girlsList.add("Yuzuki");
        girlsList.add("Rino");
        girlsList.add("Ruka");
        UsaImages.clear();
        UsaImages.add(R.drawable.cam0);
        UsaImages.add(R.drawable.cam2);
        UsaImages.add(R.drawable.cam3);
        UsaImages.add(R.drawable.cam4);
        UsaImages.add(R.drawable.cam5);
        UsaImages.add(R.drawable.cam6);
        UsaImages.add(R.drawable.cam7);
        UsaImages.add(R.drawable.cam8);
        UsaImages.add(R.drawable.cam9);
        UsaImages.add(R.drawable.cam10);
    }

    public static void AddMaxicoGirlsData() {
        girlsList.clear();
        girlsList.add("Matlida");
        girlsList.add("sadie");
        girlsList.add("thea");
        girlsList.add("Bonnie");
        girlsList.add("Paige");
        girlsList.add("Sadie");
        girlsList.add("Thea");
        girlsList.add("Bonnie");
        girlsList.add("Paige");
        girlsList.add("Eloise");
        MaxicoImages.clear();
        MaxicoImages.add(R.drawable.m0);
        MaxicoImages.add(R.drawable.m1);
        MaxicoImages.add(R.drawable.m2);
        MaxicoImages.add(R.drawable.m3);
        MaxicoImages.add(R.drawable.m4);
        MaxicoImages.add(R.drawable.m5);
        MaxicoImages.add(R.drawable.m6);
        MaxicoImages.add(R.drawable.m7);
        MaxicoImages.add(R.drawable.m8);
        MaxicoImages.add(R.drawable.m9);
        MaxicoImages.add(R.drawable.m10);
    }

    public static void AddUkGirlsData() {
        girlsList.clear();
        girlsList.add("Addison");
        girlsList.add("Elizabt");
        girlsList.add("Rie");
        girlsList.add("Alexis");
        girlsList.add("Lsabella");
        girlsList.add("Emma");
        girlsList.add("Olivia");
        girlsList.add("sadie");
        girlsList.add("thea");
        girlsList.add("Bonnie");
        UkImages.clear();
        UkImages.add(R.drawable.uk0);
        UkImages.add(R.drawable.uk1);
        UkImages.add(R.drawable.uk3);
        UkImages.add(R.drawable.uk4);
        UkImages.add(R.drawable.uk5);
        UkImages.add(R.drawable.uk6);
        UkImages.add(R.drawable.uk7);
        UkImages.add(R.drawable.uk8);
        UkImages.add(R.drawable.uk9);
        UkImages.add(R.drawable.uk10);
    }

    public static void AddSpanishGirlsData() {
        girlsList.clear();
        girlsList.add("Alyssa");
        girlsList.add("madeline");
        girlsList.add("lerry");
        girlsList.add("charlotte");
        girlsList.add("Lsabella");
        girlsList.add("Emma");
        girlsList.add("Olivia");
        girlsList.add("Sophia");
        girlsList.add("Lsabella");
        girlsList.add("Iosw");
        SpanishImages.clear();
        SpanishImages.add(R.drawable.spns0);
        SpanishImages.add(R.drawable.spns2);
        SpanishImages.add(R.drawable.spns3);
        SpanishImages.add(R.drawable.spns4);
        SpanishImages.add(R.drawable.spns5);
        SpanishImages.add(R.drawable.spns6);
        SpanishImages.add(R.drawable.spns7);
        SpanishImages.add(R.drawable.spns8);
        SpanishImages.add(R.drawable.spns9);
        SpanishImages.add(R.drawable.spns10);

    }

    public static void AddChineseGirlsData() {
        girlsList.clear();
        girlsList.add("Dongmei");
        girlsList.add("Ehuang");
        girlsList.add("Fenfang");
        girlsList.add("Anna");
        girlsList.add("Lanying");
        girlsList.add("Huifen");
        girlsList.add("Meihui");
        girlsList.add("Gangika");
        girlsList.add("Wenquin");
        girlsList.add("Quingge");
        girlsList.add("Kuemigs");
        ChineseImages.clear();
        ChineseImages.add(R.drawable.chns0);
        ChineseImages.add(R.drawable.chns2);
        ChineseImages.add(R.drawable.chns3);
        ChineseImages.add(R.drawable.chns4);
        ChineseImages.add(R.drawable.chns5);
        ChineseImages.add(R.drawable.chns6);
        ChineseImages.add(R.drawable.chns7);
        ChineseImages.add(R.drawable.chns8);
        ChineseImages.add(R.drawable.chns9);
        ChineseImages.add(R.drawable.chns10);
        ChineseImages.add(R.drawable.chns11);

    }

    public static void AddAustraliaGirlsData() {
        girlsList.clear();
        girlsList.add("Matilda");
        girlsList.add("Sadie");
        girlsList.add("Thea");
        girlsList.add("Bonnie");
        girlsList.add("Paige");
        girlsList.add("Eloise");
        girlsList.add("Addison");
        girlsList.add("Elizabt");
        girlsList.add("Rie");
        girlsList.add("Alexis");
        AustraliaImages.clear();
        AustraliaImages.add(R.drawable.aus0);
        AustraliaImages.add(R.drawable.aus2);
        AustraliaImages.add(R.drawable.aus3);
        AustraliaImages.add(R.drawable.aus4);
        AustraliaImages.add(R.drawable.aus5);
        AustraliaImages.add(R.drawable.aus6);
        AustraliaImages.add(R.drawable.aus7);
        AustraliaImages.add(R.drawable.aus8);
        AustraliaImages.add(R.drawable.aus9);
        AustraliaImages.add(R.drawable.aus10);

    }

    public static void AddCanadaGirlsData() {
        girlsList.clear();
        girlsList.add("Lsabella");
        girlsList.add("Mia");
        girlsList.add("Louise");
        girlsList.add("Alexia");
        girlsList.add("Lina");
        girlsList.add("Sakura");
        girlsList.add("Mio");
        girlsList.add("kate");
        girlsList.add("Kiera");
        girlsList.add("Carly");
        CanadaImages.clear();
        CanadaImages.add(R.drawable.can0);
        CanadaImages.add(R.drawable.can2);
        CanadaImages.add(R.drawable.can3);
        CanadaImages.add(R.drawable.can4);
        CanadaImages.add(R.drawable.can5);
        CanadaImages.add(R.drawable.can6);
        CanadaImages.add(R.drawable.can7);
        CanadaImages.add(R.drawable.can8);
        CanadaImages.add(R.drawable.can9);
        CanadaImages.add(R.drawable.can10);


    }

    public static void AddPakistanGirlsData() {
        girlsList.clear();
        girlsList.add("Sarah");
        girlsList.add("laura");
        girlsList.add("emmly");
        girlsList.add("ami");
        girlsList.add("louise");
        girlsList.add("jenifer");
        girlsList.add("Roza");
        girlsList.add("lisle");
        girlsList.add("nozomi");
        girlsList.add("rie");
        girlsList.add("kate");
        PakistanImages.clear();
        PakistanImages.add(R.drawable.pak0);
        PakistanImages.add(R.drawable.pak2);
        PakistanImages.add(R.drawable.pak3);
        PakistanImages.add(R.drawable.pak4);
        PakistanImages.add(R.drawable.pak5);
        PakistanImages.add(R.drawable.pak6);
        PakistanImages.add(R.drawable.pak7);
        PakistanImages.add(R.drawable.pak8);
        PakistanImages.add(R.drawable.pak9);
        PakistanImages.add(R.drawable.p2);
        PakistanImages.add(R.drawable.p1);

    }

    public static void AddJapanGirlsData() {
        girlsList.clear();
        girlsList.add("Mao");
        girlsList.add("Yuzuki");
        girlsList.add("Rino");
        girlsList.add("Ruka");
        girlsList.add("Airi");
        girlsList.add("Chizue");
        girlsList.add("Etsudo");
        girlsList.add("Hanami");
        girlsList.add("Kaede");
        girlsList.add("Mayumi");
        JapanImages.clear();
        JapanImages.add(R.drawable.jap0);
        JapanImages.add(R.drawable.jap2);
        JapanImages.add(R.drawable.jap3);
        JapanImages.add(R.drawable.jap4);
        JapanImages.add(R.drawable.jap5);
        JapanImages.add(R.drawable.jap6);
        JapanImages.add(R.drawable.jap7);
        JapanImages.add(R.drawable.jap8);
        JapanImages.add(R.drawable.jap9);
        JapanImages.add(R.drawable.jap10);
    }

    public static void AddRussiaGirlsData() {
        girlsList.clear();
        girlsList.add("Dongmei");
        girlsList.add("Ehuang");
        girlsList.add("Fenfang");
        girlsList.add("Anna");
        girlsList.add("Mao");
        girlsList.add("Yuzuki");
        girlsList.add("Rino");
        girlsList.add("sadie");
        girlsList.add("thea");
        RussiaImages.clear();
        RussiaImages.add(R.drawable.bel0);
        RussiaImages.add(R.drawable.bel2);
        RussiaImages.add(R.drawable.bel3);
        RussiaImages.add(R.drawable.bel4);
        RussiaImages.add(R.drawable.bel5);
        RussiaImages.add(R.drawable.bel6);
        RussiaImages.add(R.drawable.bel7);
        RussiaImages.add(R.drawable.bel8);
        RussiaImages.add(R.drawable.bel9);
        RussiaImages.add(R.drawable.bel10);

    }
}


