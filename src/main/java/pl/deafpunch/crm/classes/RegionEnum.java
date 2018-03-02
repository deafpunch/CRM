package pl.deafpunch.crm.classes;

public enum RegionEnum {

	DOLNOSLASKIE("woj.dolnośląskie"),
	KUJAWSKO_POMORSKIE("woj.kujawsko-pomorskie"),
	LUBELSKIE("woj.lubelskie"),
	LUBUSKIE("woj.lubuskie"),
	LODZKIE("woj.łódzkie"),
	MALOPOLSKIE("woj.małopolskie"),
	MAZOWIECKIE("woj.mazowieckie"),
	OPOLSKIE("woj.opolskie"),
	PODKARPACKIE("woj.podkarpackie"),
	PODLASKIE("woj.podlaskie"),
	POMORSKIE("woj.pomorskie"),
	SLASKIE("woj.śląskie"),
	SWIETOKRZYSTKIE("woj.świętokrzyskie"),
	WARMINSKO_MAZURSKIE("woj.warmińsko-mazurskie"),
	WIELKOPOLSKIE("woj.wielkopolskie"),
	ZACHODNIOPOMORSKIE("woj.zachodniopomorskie");
	
	private String name;

    RegionEnum(String region) {
        this.name = region;
    }

    public String region() {
        return name;
    }
	
}
