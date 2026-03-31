# OV-chipkaart Project Ontwerpdocument

**Versie:** 1.0  
**Datum:** 30 maart 2026  
**Project:** OOP 1 - Casus - Sprint 2  
**Focus:** Anonieme OV-chipkaart - Uitchecken

---

## 1. Use Case Diagram

```
┌─────────────────────────────────────────────────────────────────────────┐
│                        OV-chipkaart Systeem                             │
│  ┌─────────────────────────────────────────────────────────────────┐    │
│  │                                                                   │    │
│  │  ┌──────────┐    ┌────────────┐    ┌───────────┐               │    │
│  │  │Inchecken │    │ Uitchecken │    │ Top-up    │               │    │
│  │  └───┬──────┘    └──────┬─────┘    └─────┬─────┘               │    │
│  │      │                  │                │                      │    │
│  │  ┌───┴────────┐   ┌────┴─────┐   ┌──────┴──────┐               │    │
│  │  │Dagkaart    │   │Ritprijs  │   │Auto top-up  │               │    │
│  │  │kopen       │   │berekenen │   │instellen    │               │    │
│  │  └────────────┘   └──────────┘   └─────────────┘               │    │
│  │                         │                                      │    │
│  │                    ┌────┴────┐                                 │    │
│  │                    │Transactie│                                 │    │
│  │                    │bekijken │                                 │    │
│  │                    └─────────┘                                 │    │
│  └─────────────────────────────────────────────────────────────────┘    │
│                                                                         │
│         │                    │                    │                     │
│    ┌────┴────┐          ┌────┴────┐          ┌───┴───┐                 │
│    │Reiziger │          │ Station │          │  NS   │                 │
│    │(Anoniem)│          │(Poort)  │          │Systeem│                 │
│    └─────────┘          └─────────┘          └───────┘                 │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## 2. Systeemafbakening

**Systeem:** OV-chipkaart Systeem

**Binnen het systeem:**
- Saldo beheer op de kaart
- In- en uitchecken bij stations
- Ritprijs berekening
- Transactiehistorie
- Top-up functionaliteit
- Accountbeheer
- Dagkaart functionaliteit

**Buiten het systeem:**
- Fysieke OV-chipkaart hardware
- Bank/betaalsystemen (externe integratie)
- Vervoerdersinformatie (externe databases)

---

## 3. Actors

| Actor | Beschrijving | Type |
|-------|--------------|------|
| **Reiziger (Anoniem)** | Heeft een anonieme OV-chipkaart zonder naamgeving. Kan opladen, in- en uitchecken. | Primair |
| **Station/Poort** | Fysiek systeem dat in- en uitchecken registreert. | Secundair |
| **NS Systeem** | Centraal systeem voor ritprijzen en abonnementen. | Secundair |

---

## 4. Use Cases

### 4.1 Use Case: Inchecken

| Element | Beschrijving |
|---------|--------------|
| **ID** | UC-01 |
| **Naam** | Inchecken |
| **Beschrijving** | Reiziger checkt in bij een station met zijn anonieme OV-chipkaart |
| **Primaire actor** | Reiziger |
| **Secundaire actor** | Station/Poort |

**Precondities:**
- Reiziger heeft een geldige (niet verlopen) OV-chipkaart
- Reiziger is niet reeds ingecheckt
- Saldo is minimaal €20, OF dagkaart is actief
- Kaart is niet geblokkeerd

**Postcondities:**
- Reiziger is ingecheckt bij het opgegeven station
- Check-in tijd is geregistreerd
- Systeem is klaar voor uitchecken

**Normaal verloop:**
1. Reiziger houdt kaart tegen bij de incheck-poort
2. Systeem leest kaartgegevens
3. Systeem controleert of reiziger niet al is ingecheckt
4. Systeem controleert saldo (minimaal €20 of dagkaart actief)
5. Systeem registreert check-in station, transporttype en tijdstip
6. Poort geeft groen licht / acceptatiegeluid

**Alternatieve scenario's:**
- 4a. Saldo te laag → Poort geeft foutmelding, transactie geweigerd
- 4b. Dagkaart actief → Check-in toegestaan ondanks laag saldo
- 4c. Kaart verlopen → Check-in geweigerd,melding aan reiziger
- 4d. Kaart geblokkeerd → Check-in geweigerd,melding aan reiziger

---

### 4.2 Use Case: Uitchecken

| Element | Beschrijving |
|---------|--------------|
| **ID** | UC-02 |
| **Naam** | Uitchecken |
| **Beschrijving** | Reiziger checkt uit bij het eindstation, ritprijs wordt berekend en afgeschreven |
| **Primaire actor** | Reiziger |
| **Secundaire actor** | Station/Poort, NS Systeem |

**Precondities:**
- Reiziger is ingecheckt bij een station
- Kaart is niet verlopen
- Kaart is niet geblokkeerd

**Postcondities:**
- Ritprijs is berekend en afgeschreven van saldo
- Reiziger is niet meer ingecheckt
- Transactie is geregistreerd in het systeem
- Auto top-up is geactiveerd indien van toepassing
- Kaartstatus is bijgewerkt (loyaliteitpunten, statistieken)

**Normaal verloop:**
1. Reiziger houdt kaart tegen bij de uitcheck-poort
2. Systeem leest kaartgegevens en huidige check-in
3. Systeem bepaalt eindstation (huidige locatie)
4. Systeem berekent ritprijs op basis van:
   - Afstand (verschil kilometer markers)
   - Transporttype (trein/bus/tram/metro)
   - Reisduur
   - Klasse (1e of 2e)
   - Transfer-korting (binnen 35 minuten)
   - Abonnement-kortingen
5. Systeem schrijft ritprijs af van saldo
6. Systeem registreert transactie
7. Poort geeft groen licht / acceptatiegeluid

**Alternatieve scenario's:**
- 4a. Dagkaart actief → Rit is gratis (€0)
- 4b. Transfer binnen 35 min → Geen basisprijs, alleen kilometerprijs
- 4c. Bus/Tram transfer → Rit is gratis
- 4d. Dal Voordeel actief (buiten spits) → 40% korting
- 4e. Weekend Vrij actief (weekend) → Rit is gratis
- 4f. Altijd Voordeel actief → 20% korting
- 4g. 1e klas reiziger (trein) → 1.5x prijs
- 4h. Spitsuren (werkdagen 6:30-9:00, 16:00-18:30) → 1.2x prijs

**Uitzonderingen:**
- Geen actieve check-in → Foutmelding "Niet ingecheckt"
- Kaart verlopen → Uitchecken geweigerd
- Kaart geblokkeerd → Uitchecken geweigerd

---

### 4.3 Use Case: Saldo Opwaarderen (Top-up)

| Element | Beschrijving |
|---------|--------------|
| **ID** | UC-03 |
| **Naam** | Top-up |
| **Beschrijving** | Reiziger waardeert saldo op via machine of online |
| **Primaire actor** | Reiziger |

**Precondities:**
- Kaart is niet verlopen
- Kaart is niet geblokkeerd

**Postcondities:**
- Saldo is verhoogd met het opgegeven bedrag
- Transactie is geregistreerd
- Anonieme kaarten blijven onder €150 maximum

**Normaal verloop:**
1. Reiziger selecteert top-up optie
2. Reiziger kiest bedrag:
   - Machine: minimaal €5, maximaal €150
   - Online: minimaal €10, maximaal €150
3. Systeem controleert of bedrag geldig is
4. Systeem verhoogt saldo
5. Anonieme kaarten: controleer of max €150 niet overschreden
6. Transactie wordt geregistreerd
7. Als kaart geblokkeerd was en saldo ≥ €0 → Kaart wordt ontgrendeld

---

### 4.4 Use Case: Transactiehistorie Bekijken

| Element | Beschrijving |
|---------|--------------|
| **ID** | UC-04 |
| **Naam** | Transacties Bekijken |
| **Beschrijving** | Reiziger bekijkt de geschiedenis van alle transacties |
| **Primaire actor** | Reiziger |

**Precondities:**
- Geen (alleen kaart nodig om te selecteren)

**Postcondities:**
- Lijst met transacties wordt getoond

**Normaal verloop:**
1. Reiziger opent transactiescherm
2. Systeem toont alle transacties met datum, tijd, type, bedrag en saldo
3. Optioneel: filteren op transactietype

---

### 4.5 Use Case: Dagkaart Kopen

| Element | Beschrijving |
|---------|--------------|
| **ID** | UC-05 |
| **Naam** | Dagkaart Kopen |
| **Beschrijving** | Reiziger koopt een dagkaart voor gratis reizen |
| **Primaire actor** | Reiziger |

**Precondities:**
- Kaart is niet verlopen
- Kaart is niet geblokkeerd
- Saldo is minimaal €22.50

**Postcondities:**
- Dagkaart is geactiveerd voor vandaag
- Saldo is verminderd met €22.50
- Reizen is gratis voor de rest van de dag

**Normaal verloop:**
1. Reiziger selecteert "Dagkaart kopen"
2. Systeem controleert saldo (minimaal €22.50)
3. Systeem rekent €22.50 af
4. Dagkaart is actief voor vandaag

---

### 4.6 Use Case: Auto Top-up Instellen

| Element | Beschrijving |
|---------|--------------|
| **ID** | UC-06 |
| **Naam** | Auto Top-up |
| **Beschrijving** | Reiziger stelt automatisch opladen in bij laag saldo |
| **Primaire actor** | Reiziger |

**Precondities:**
- Kaart is niet verlopen
- Kaart is niet geblokkeerd

**Postcondities:**
- Auto top-up is ingeschakeld met opgegeven drempel en bedrag

**Normaal verloop:**
1. Reiziger opent auto top-up instellingen
2. Reiziger stelt drempelwaarde in (bijv. €10)
3. Reiziger stelt oplaadbedrag in (bijv. €20)
4. Systeem activeert auto top-up
5. Na uitchecken: als saldo < drempel → automatisch opladen

---

### 4.7 Use Case: Laatste Rit Terugbetalen

| Element | Beschrijving |
|---------|--------------|
| **ID** | UC-07 |
| **Naam** | Refund |
| **Beschrijving** | Reiziger vraagt restitutie voor de laatste rit |
| **Primaire actor** | Reiziger |

**Precondities:**
- Er is minimaal één rit voltooid

**Postcondities:**
- Laatste ritbedrag is teruggestort
- Saldo is verhoogd
- Reistellingen zijn bijgewerkt

---

## 5. Domeinmodel (Klassen)

```
┌─────────────────────────────────────────────────────────────────────────┐
│                              Account                                    │
├─────────────────────────────────────────────────────────────────────────┤
│ - name: String                                                          │
│ - email: String                                                        │
│ - password: String                                                     │
│ - cards: List<OVCard>                                                  │
├─────────────────────────────────────────────────────────────────────────┤
│ + addCard(personal: boolean): OVCard                                    │
│ + removeCard(index: int): boolean                                      │
│ + saveToFile(folder: String): void                                     │
│ + loadFromFile(folder: String, email: String): Account                 │
└─────────────────────────────────────────────────────────────────────────┘
                                    │
                                    │ 1..*
                                    ▼
┌─────────────────────────────────────────────────────────────────────────┐
│                                OVCard                                   │
├─────────────────────────────────────────────────────────────────────────┤
│ - cardNumber: String                                                   │
│ - balance: double                                                      │
│ - personal: boolean                                                   │
│ - blocked: boolean                                                    │
│ - expiryDate: LocalDate                                               │
│ - travelClass: TravelClass                                             │
│ - subscription: Subscription                                           │
│ - transactions: List<Transaction>                                     │
│ - checkedInAt: Station                                                │
│ - checkedInTransport: TransportType                                   │
│ - checkInTime: LocalDateTime                                          │
│ - autoTopUpEnabled: boolean                                           │
│ - lastCheckoutTime: LocalDateTime                                     │
│ - dayPassActive: boolean                                              │
├─────────────────────────────────────────────────────────────────────────┤
│ + checkIn(station: Station, transport: TransportType): boolean         │
│ + checkOut(station: Station): double                                   │
│ + topUp(amount: double, source: String): boolean                        │
│ + buyDayPass(): boolean                                                │
│ + refundLastTrip(): boolean                                            │
│ + applyMissedCheckoutPenalty(): double                                 │
│ + isBlocked(): boolean                                                 │
│ + isExpired(): boolean                                                 │
│ + saveToFile(filename: String): void                                   │
│ + loadFromFile(filename: String): OVCard                               │
└─────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────┐
│                               Station                                   │
├─────────────────────────────────────────────────────────────────────────┤
│ - name: String                                                         │
│ - kilometerMarker: double                                             │
│ - availableTransport: List<TransportType>                              │
├─────────────────────────────────────────────────────────────────────────┤
│ + hasTransportType(type: TransportType): boolean                        │
└─────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────┐
│                             Transaction                                 │
├─────────────────────────────────────────────────────────────────────────┤
│ - dateTime: LocalDateTime                                             │
│ - type: String                                                         │
│ - amount: double                                                       │
│ - balanceAfter: double                                                │
│ - description: String                                                 │
└─────────────────────────────────────────────────────────────────────────┘

┌──────────────┐     ┌──────────────┐     ┌──────────────┐
│ TravelClass  │     │ TransportType│     │ Subscription │
├──────────────┤     ├──────────────┤     ├──────────────┤
│ FIRST        │     │ TRAIN        │     │ NONE         │
│ SECOND       │     │ BUS          │     │ DAL_VOORDEEL │
│              │     │ TRAM         │     │ WEEKEND_VRIJ │
│              │     │ METRO        │     │ ALTIJD_VOORDEEL
└──────────────┘     └──────────────┘     └──────────────┘
```

---

## 6. Domeinregels

### 6.1 Prijsberekening

| Transport | Basisprijs | Per km | Multiplier |
|------------|------------|--------|------------|
| Trein | €0.89 | €0.19 | - |
| Metro | €1.08 | €0.10 | - |
| Bus | €1.08 | - | Flat |
| Tram | €1.08 | - | Flat |

### 6.2 Kortingen en Toeslagen

| Regel | Voorwaarde | Effect |
|-------|------------|--------|
| Transfer | Check-in binnen 35 min na laatste uitcheck | Geen basisprijs |
| Bus/Tram transfer | Binnen 35 min | Rit gratis |
| Dal Voordeel | Buiten spitsuren | 40% korting |
| Weekend Vrij | Weekend (za/zo) | Rit gratis |
| Altijd Voordeel | Altijd | 20% korting |
| 1e klas (trein) | Reiziger in 1e klas | 1.5x prijs |
| Spits toeslag | Werkdagen 6:30-9:00, 16:00-18:30 | 1.2x prijs |

### 6.3 Kaartbeperkingen

| Regel | Anoniem | Persoonlijk |
|-------|---------|-------------|
| Maximum saldo | €150 | Onbeperkt |
| Vervaldatum | 5 jaar | 5 jaar |
| Blokkering | Bij saldo < -€30 | Bij saldo < -€30 |

### 6.4 Minimum Bedragen

| Top-up methode | Minimum | Maximum |
|----------------|---------|---------|
| Machine | €5 | €150 |
| Online | €10 | €150 |
| Dagkaart | €22.50 | €22.50 |

### 6.5 Check-in Vereisten

- Minimum saldo: €20 (of actieve dagkaart)
- Kaart niet verlopen
- Kaart niet geblokkeerd
- Niet reeds ingecheckt

---

## 7. Uitgangspunten

### Voor Sprint 2 (Focus: Uitchecken)

1. **Anonieme kaarten:** Focus op anonieme OV-chipkaarten zonder naam
2. **Enkele kaart:** Reiziger heeft één actieve kaart
3. **Enkelvoudige reis:** Geen complexe overstap-scenario's
4. **Prijsberekening:** Volledig geïmplementeerd volgens domeinregels
5. **Transfer:** Binnen 35 minuten na laatste uitcheck
6. **Abonnementen:** Ondersteund maar focus ligt op standaard ritten
7. **Data persistentie:** Transacties worden opgeslagen

---

## 8. Revisiegeschiedenis

| Versie | Datum | Wijziging |
|--------|-------|-----------|
| 1.0 | 30-03-2026 | Initiële versie voor Sprint 2 |

---

## Bijlage A: Voorbeeld Ritprijs Berekening

**Situatie:**
- Van: Amsterdam Centraal (km 0.0)
- Naar: Schiphol Airport (km 15.5)
- Transport: Trein
- Klasse: 2e klasse
- Geen abonnement
- Geen transfer (eerste rit van de dag)
- Tijd: Woensdag 10:00 (geen spits)

**Berekening:**
```
Afstand = |15.5 - 0.0| = 15.5 km

Basisprijs = €0.89
Kilometerprijs = 15.5 × €0.19 = €2.945
Subtotaal = €0.89 + €2.945 = €3.835

Geen transfer → geen korting
Geen spits → geen toeslag
Geen abonnement → geen korting

Eindtotaal = €3.84 (afgerond)
```

---

## Bijlage B: Prioriteit Use Cases

| ID | Use Case | Prioriteit | Sprint |
|----|----------|-------------|--------|
| UC-01 | Inchecken | Hoog | 1 |
| UC-02 | Uitchecken | Hoog | 2 |
| UC-03 | Top-up | Hoog | 1 |
| UC-04 | Transacties Bekijken | Medium | 1 |
| UC-05 | Dagkaart Kopen | Medium | 2 |
| UC-06 | Auto Top-up | Laag | 2 |
| UC-07 | Refund | Laag | 2 |

---

*Document gegenereerd voor OOP1 Casus - Sprint 2*
