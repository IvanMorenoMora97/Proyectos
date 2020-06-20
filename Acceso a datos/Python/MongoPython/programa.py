import pymongo
from pymongo import MongoClient
from mongoengine import *
import datetime
import random
from pprint import pprint
from datetime import date
from datetime import datetime
from random import randrange, uniform
from bson.objectid import ObjectId
client = MongoClient()
# nos conectamos a db project
db = client["project"]
# nos conectamos a la collection pokemon que esta dentro de db (por tanto de project)
collPokemon = db["pokemon"]
collTeam = db["team"]
collTeam1=db["team1"]
collTeam2=db["team2"]
collMoves = db["moves"]
# Funciones para separar el codigo
# Funcion search
validarsearch = ["num", "img", "type","height","weight","candy","candy_count","egg","spawn_chance","avg_spawns","spawn_time","multipliers","weakness","prev_evolution","next_evolution","all"]
def programageneral():

    def search(sentencia):
        # HACER CONSULTA CON EL DIGITO
        if sentencia[2] in validarsearch:   
            if(sentencia[1].isdigit()):
                pprint("Buscando pokemon por digito....")
                #Hay que parsear a int
                NPokemon=int(sentencia[1])
                # HACER CONSULTA CON HEIGHT
                condicion = sentencia[2].lower()
                if(condicion == "all"):
                    pprint("all")
                    prueba = collPokemon.find_one({"id": NPokemon})
                    return prueba
                else:
                    pprint("height con numero de pokemon")
                    prueba = collPokemon.find_one({"id":NPokemon}, {
                                            "_id": 0, "num": 1, sentencia[2]: 1})
                    return prueba
            # BUSCAMOS AL POKEMON POR SU NOMBRE
            else:
                pprint("Buscando pokemon por nombre....")
                Pokemon = sentencia[1]
                # HACER CONSULTA CON HEIGHT
                condicion = sentencia[2].lower()
                if (condicion!="all"):
                    prueba = collPokemon.find_one({"name": Pokemon}, {
                                            "_id": 0, "name": 1,sentencia[2]: 1})
                    return prueba
                else:           
                    prueba = collPokemon.find_one({"name": Pokemon})
                    return prueba                
        return "Comando no valido, porfavor para saber los comandos disponibles escriba --help"
    # Funcion release

    def release(sentencia):
        pprint("Has entrado en el release")

        if(sentencia[1].isdigit()):
            nPokemonLiberar = sentencia[1]

            # PRIMERO SE HACE UNA CONSULTA PARA SABER SI EL POKEMON QUE SE QUIERE LIBERAR ESTA EN EL EQUIPO
            consulta = collTeam.find_one({"id": int(nPokemonLiberar)}, {
                                    "name": 1, "num": 1})

            # COMPROBAMOS QUE HAY POKEMONS EN EL EQUIPO
            if(consulta!=None):

                # SI HAY POKEMONS EN EL EQUIPO PROCEDEMOS A ELIMINARLOS
                # CONSULTA
                pprint(
                    consulta["name"] + " alliberat. Numero de l' alliberat: " + consulta["num"])
                collTeam.remove({"id": int(nPokemonLiberar)})

                pokemonsEquipo = collTeam.find()

                if(pokemonsEquipo.count() > 0):
                    pprint("Pokémons actuals en l'equip: " +
                        pokemonsEquipo.count())
                else:
                    pprint("No hay Pokémons en el equipo.")

            else:
                pprint("Este pokemon no esta en el equipo")

        else:
            # pprint("STRING")
            pokemonLiberar = sentencia[1]

            # PRIMERO SE HACE UNA CONSULTA PARA SABER SI EL POKEMON QUE SE QUIERE LIBERAR ESTA EN EL EQUIPO
            consulta = collTeam.find_one({"name": pokemonLiberar}, {
                                    "name": 1, "num": 1})

            # COMPROBAMOS QUE HAY POKEMONS EN EL EQUIPO
            if(consulta!=None):

                # SI HAY POKEMONS EN EL EQUIPO PROCEDEMOS A ELIMINARLOS
                # CONSULTA
                pprint(
                    consulta["name"] + " alliberat. Numero de l' alliberat: " + consulta["num"])
                collTeam.remove({"name": pokemonLiberar})

                pokemonsEquipo = collTeam.find()

                if(pokemonsEquipo.count() > 0):
                    pprint("Pokémons actuals en l'equip: " + pokemonsEquipo.count())

                else:
                    pprint("No hay Pokémons en el equipo.")

            else:
                pprint("Este pokemon no esta en el equipo")

        pprint("<============>")
        pprint("EQUIPO ACTUAL")
        pprint("<============>")
        equipo = collTeam.find()

        for ee in equipo:
            pprint(ee)

#Funcion del update
    def training(sentencia):
        cPokemon = sentencia[1]
            # INTRODUCCIÓN DEL NUMERO DEL POKEMON (Candy 152)
        if(cPokemon.isdigit()):
            pprint("Entrenamiento de pokemon por digito...")
            # COMPROBAR SI EL POKÉMON NO HA LLEGADO AL MÁXIMO DE CARAMELOS PARA SU EVOLUCION Y EXISTE EL POKEMON EN EL EQUIPO
            consulta=collTeam.find_one({"id":int(cPokemon),"candy_count":{"$exists":True}})
        
            if(consulta!=None):
                nPokemon = consulta["num"]
                nombrePokemon = consulta["name"]
                caramelosActuales = consulta["current_candy"]
                caramelosMaximos = consulta["candy_count"]
                # INCREMENTA EN +1 EL VALOR DE CURRENT_CANDY DEL NUMERO SELECCIONADO, SE LE PASA EL NUMERO DEL POKEMON COMO SI FUESE SU ID DE LA POKÉDEX (EJEMPLO: Chikorita tiene el numero de Pokédex 152, este numero se le pasa a la consulta)
                incremento = collTeam.update_one(
                        {"num": nPokemon}, {"$inc": {"current_candy": 1}})
                consulta2 = collTeam.find(
                        {"num": cPokemon}, {"num": 1, "name": 1, "current_candy": 1, "candy_count": 1})
                pprint("Se le ha dado un caramelo al Pokémon " + nombrePokemon +
                ", caramelos actuales del pokémon " + str(consulta2[0]["current_candy"]))
                    #Si el campo es igual de current_candy y candy_count evoluciona
                if(consulta2[0]["current_candy"]>=consulta2[0]["candy_count"]):
                    # Cogemos campos de next_evolution
                    consultaevo=collPokemon.find_one({"id":int(cPokemon)})
                    nameEvolution = consultaevo["next_evolution"][0]["name"]
                    numEvolution = consultaevo["next_evolution"][0]["num"]
                    #Cogemos los caramelos que necesita el siguiente pokemon
                    candyCount = collPokemon.find_one({"name": nameEvolution,"candy_count":{"$exists":True}}, {"candy_count": 1,"candy":1})
                    #Finalmente updateamos el pokemon
                    if(candyCount!=None):
                        evolucion = collTeam.update_one({"num": nPokemon}, {"$set": {"id": int(numEvolution), "name": nameEvolution, "num": numEvolution, "current_candy": 0,"candy":candyCount["candy"]}, "$inc": {"CP": 100}})
                        pprint(nombrePokemon + " evoluciona a " + nameEvolution)
                    else:
                        evolucion = collTeam.update_one({"num": nPokemon}, {"$set": {"id": int(numEvolution), "name": nameEvolution, "num": numEvolution}, "$inc": {"CP": 100},"$unset":{"candy_count":1,"current_candy":1}})
                        pprint(nombrePokemon + " evoluciona a " + nameEvolution)
                    
            else:
                pprint("Num de pokedex no valido o este pokemon no puede recibir caramelos")
        else:
            pprint("Etrenamiento de pokemon por nombre...")   
             # COMPROBAR SI EL POKÉMON NO HA LLEGADO AL MÁXIMO DE CARAMELOS PARA SU EVOLUCION Y EXISTE EL POKEMON EN EL EQUIPO
            consulta=collTeam.find_one({"name":cPokemon,"candy_count":{"$exists":True}})
            if(consulta!=None):
                nPokemon = consulta["num"]
                nombrePokemon = consulta["name"]
                caramelosActuales = consulta["current_candy"]
                caramelosMaximos = consulta["candy_count"]
                # INCREMENTA EN +1 EL VALOR DE CURRENT_CANDY DEL NUMERO SELECCIONADO, SE LE PASA EL NUMERO DEL POKEMON COMO SI FUESE SU ID DE LA POKÉDEX (EJEMPLO: Chikorita tiene el numero de Pokédex 152, este numero se le pasa a la consulta)
                incremento = collTeam.update_one(
                        {"num": nPokemon}, {"$inc": {"current_candy": 1}})
                consulta2 = collTeam.find(
                        {"name": cPokemon}, {"num": 1, "name": 1, "current_candy": 1, "candy_count": 1})
                pprint("Se le ha dado un caramelo al Pokémon " + nombrePokemon +
                ", caramelos actuales del pokémon " + str(consulta2[0]["current_candy"]))
                    #Si el campo es igual de current_candy y candy_count evoluciona
                if(consulta2[0]["current_candy"]>=consulta2[0]["candy_count"]):
                    # Cogemos campos de next_evolution
                    consultaevo=collPokemon.find_one({"name":cPokemon})
                    nameEvolution = consultaevo["next_evolution"][0]["name"]
                    numEvolution = consultaevo["next_evolution"][0]["num"]
                    #Cogemos los caramelos que necesita el siguiente pokemon
                    candyCount = collPokemon.find_one({"name": nameEvolution,"candy_count":{"$exists":True}}, {"candy_count": 1,"candy":1})
                    #Finalmente updateamos el pokemon
                    if(candyCount!=None):
                        evolucion = collTeam.update_one({"num": nPokemon}, {"$set": {"id": int(numEvolution), "name": nameEvolution, "num": numEvolution, "current_candy": 0,"candy":candyCount["candy"]}, "$inc": {"CP": 100}})
                        pprint(nombrePokemon + " evoluciona a " + nameEvolution)
                    else:
                        evolucion = collTeam.update_one({"num": nPokemon}, {"$set": {"id": int(numEvolution), "name": nameEvolution, "num": numEvolution}, "$inc": {"CP": 100},"$unset":{"candy_count":1,"current_candy":1}})
                        pprint(nombrePokemon + " evoluciona a " + nameEvolution)
            else:
                pprint("Num de pokedex no valido o este pokemon no puede recibir caramelos")
    def catch(sentencia):
            pokemon = sentencia[1]
            if(pokemon.isdigit()):
                consultaPokemon = collPokemon.find_one({"id":int(pokemon)},{"id":1,"num":1,"name":1,"type":1,"candy_count":1,"candy":1,"weaknesses":1})
                #La siguiente consulta se hace ya que es posible que el pokemon no tenga el field candy_count
                consultaPokemon2=collPokemon.find_one({"id":int(pokemon),"candy_count" :{"$exists":True}})
            else:
                consultaPokemon = collPokemon.find_one({"name":pokemon},{"id":1,"num":1,"name":1,"type":1,"candy_count":1,"candy":1,"weaknesses":1})
                #La siguiente consulta se hace ya que es posible que el pokemon no tenga el field candy_count
                consultaPokemon2=collPokemon.find_one({ "name":pokemon,"candy_count" :{"$exists":True}})
            #GENERAR CAMPOS ALEATORIOS DE HPmax, ATK, DEF
            hpMax = randrange(200,1000)
            atk = randrange(10,50)
            defensa = randrange(10,50)
            CP = hpMax + atk + defensa
            #SELECCIONAR DOS MOVIMIENTOS RANDOM
            movimientos = collMoves.find({"energyGain":{"$exists":True}})
            movimientos2 = collMoves.find({"energyCost":{"$exists":True}})
            movimiento1 = movimientos[randrange(1,movimientos.count())]
            movimiento2 = movimientos2[randrange(1,movimientos2.count())]
            catch_date = datetime.now()
            #hp es igual que hpMax
            hp=hpMax
            if (consultaPokemon!=None):
                if (consultaPokemon2):
                    pprint("POKEMON TIENE EL CAMPO CANDY_COUNT")
                    #AÑADIR TODOS LOS DATOS AL EQUIPO (HACER EL INSERT)
                    pokkemon ={"id":consultaPokemon["id"],"num":consultaPokemon["num"],
                    "name":consultaPokemon["name"],"type":consultaPokemon["type"],
                    "candy":consultaPokemon["candy"],"candy_count":consultaPokemon["candy_count"],
                    "weaknesses":consultaPokemon["weaknesses"],"catch_date":catch_date,
                    "CP":CP,"HPmax":hpMax,"HP":hp,"atk":atk,"def":defensa,"energy":0,
                    "moves":{"fast":movimiento1["_id"],"charged":movimiento2["_id"]},"current_candy":0}

                else:
                    pprint("POKEMON NO TIENE EL CAMPO CANDY_COUNT")
                    pokkemon ={"id":consultaPokemon["id"],"num":consultaPokemon["num"],
                    "name":consultaPokemon["name"],"type":consultaPokemon["type"],
                    "candy":consultaPokemon["candy"],
                    "weaknesses":consultaPokemon["weaknesses"],"catch_date":catch_date,
                    "CP":CP,"HPmax":hpMax,"HP":hp,"atk":atk,"def":defensa,"energy":0,
                    "moves":{"fast":movimiento1["_id"],"charged":movimiento2["_id"]}}
            
                collTeam.insert_one(pokkemon)
                pprint("Has capturado a "+consultaPokemon["name"]+" y sus movimientos son fast: "+movimiento1["name"]+" y charged: "+movimiento2["name"])
            else:
                pprint("Pokemon inexistente en el equipo")
    def combat():
        collTeam1.drop()
        collTeam2.drop()
        catchrandoms()
        boleano = False
        #LISTA DE POKEMONS EN EL EQUIPO
        teamA = collTeam1.find()
        teamB = collTeam2.find()
        posicionA = 0
        posicionB = 0
        #CANTIDAD DE VIDA
        hpA = teamA[0]["HP"]
        hpB = teamB[0]["HP"]
        #CANTIDAD DE ENERGIA
        energiaA = teamA[0]["energy"]
        energiaB = teamB[0]["energy"]
        #CANTIDAD DE DEFENSA
        defensaA = teamA[0]["def"]
        defensaB = teamB[0]["def"]
        #ID DE LOS ATAQUES RAPIDOS
        FastA = teamA[0]["moves"]["fast"]
        FastB = teamB[0]["moves"]["fast"]
       
        ChargeA = teamA[0]["moves"]["charged"]
        ChargeB = teamB[0]["moves"]["charged"]
        #CANTIDAD DE ATAQUE
        atkA = teamA[0]["atk"]
        atkB = teamB[0]["atk"]

        #DEBILIDADES DEL PRIMER POKEMON A COMBATIR
        debilidadesA = teamA[0]["weaknesses"]
        debilidadesB = teamB[0]["weaknesses"]

        #TIPOS DEL PRIMER POKEMON A COMBATIR
        tipoA = teamA[0]["type"]
        tipoB = teamB[0]["type"]

        while boleano == False:
            #TURNO DEL EQUIPO A
            
            print("Equipo A quieres atacar o cambiar? (Escribe 'atacar' o 'cambiar')")
            accion = input("Acción: ")
            accion = accion.lower()
            if(accion=="atacar"):
                tipoAtaque = input("Ataque rápido o Ataque cargado: (Escribe 'rapido' o 'cargado')")
                tipoAtaque = tipoAtaque.lower()
               
                if(tipoAtaque=="rapido"):
                    pprint("Ataque rápido")

                    #PODER DEL ATAQUE SELECCIONADO
                    poder = collMoves.find_one({"_id":FastA},{"pwr":1})
                    #TIPO DEL ATAQUE SELECCIONADO
                    tipo = collMoves.find_one({"_id":FastA},{"type":1})
                    poder = int(poder["pwr"])
                    ataque = int(atkA)
                    defensa = int(defensaB)
                    dañoTotal = (ataque * poder) / (defensa * 2)
                    
                    pprint("DAÑO TOTAL CAUSADO")
                    pprint(dañoTotal)

                    #SI EL ATAQUE COINCIDE CON EL TIPO DEL POKEMON ATACANTE (daño x 1,5)
                    for t in tipoA:
                        if(t == tipo["type"]):
                            dañoTotal = dañoTotal * 1.5

                    pprint("DAÑO TOTAL DESPUES DEL TIPO ATACANTE")
                    pprint(dañoTotal)

                    #SI EL ATAQUE COINCIDE CON UNA DE LAS DEBILIDADES DEL POKEMON ATACADO (daño x 2)
                    for dB in debilidadesB:
                        if(dB == tipo["type"]):
                            #CALCULAR EL DAÑO
                            dañoTotal = dañoTotal * 2
                        
                    pprint("DAÑO TOTAL DESPUES DEL WEAKNESS")
                    pprint(dañoTotal)

                    #REDONDEAR EL DAÑO PARA QUE NO TENGA DECIMALES
                    dañoTotal = int(round(dañoTotal))

                    pprint("DAÑO REDONDEADO SIN DECIMALES")
                    pprint(dañoTotal)

                    
                    if(dañoTotal<teamB[posicionB]["HP"]):
                        hp = teamB[posicionB]["HP"]
                        hp = hp - dañoTotal
                        pprint("HP RESTANTE DEL POKEMON B")
                        pprint(hp)
                        
                        #SE ACTUALIZA LA VIDA
                        idPDebilitado = teamB[posicionB]["_id"]
                        idPDebilitado = ObjectId(idPDebilitado)
                                                
                        #ACTUALIZA EL PRIMER POKEMON DE LA LISTA DEL EQUIPO B (EL POKEMON QUE ESTA COMBATIENDO)
                        collTeam2.update_one({"_id":idPDebilitado},{"$set":{"HP":hp}})

                        #AUGMENTA LA ENERGIA DEL POKEMON ATACANTE
                        energyGain = collMoves.find_one({"_id":FastA},{"energyGain":1})
                        energyGain = energyGain["energyGain"]
                        energiaA = teamA[posicionA]["energy"]
                        
                        energyGain = int(energyGain)

                        if((energiaA + energyGain)<100):
                            idPokemonA = teamA[posicionA]["_id"]
                            idPokemonA = ObjectId(idPokemonA)
                            energiaTotal = energiaA + energyGain
                            collTeam1.update_one({"_id":idPokemonA},{"$set":{"energy":energiaTotal}})
                        elif((energiaA + energyGain)>100):
                            collTeam1.update_one({"_id":idPokemonA},{"$set":{"energy":100}})
                        
                    
                    else:
                        idPDebilitado = teamB[posicionB]["_id"]
                        idPDebilitado = ObjectId(idPDebilitado)
                        pprint(teamB[posicionB]["name"] + " ha sido debilitado, ya no puede combatir.")
                        collTeam2.delete_one({"_id":idPDebilitado})

                        #MIRAR CANTIDA DE POKEMONS DEL EQUIPO A
                        consultarPokemons = collTeam2.find()

                        if(consultarPokemons.count()==0):
                                pprint("El combate ha finalizado, ha ganado el equipo A.")
                                break

                        #EL EQUIPO B CAMBIA AL SIGUIENTE POKEMON
                        posicionB = 0

                        #AUGMENTA LA ENERGIA DEL POKEMON ATACANTE
                        energyGain = collMoves.find_one({"_id":FastA},{"energyGain":1})
                        energyGain = energyGain["energyGain"]
                        energiaA = teamA[posicionA]["energy"]

                        energyGain = int(energyGain)

                        if((energiaA + energyGain)<100):
                            idPokemonA = teamA[posicionA]["_id"]
                            idPokemonA = ObjectId(idPokemonA)
                            energiaTotal = energiaA + energyGain
                            collTeam1.update_one({"_id":idPokemonA},{"$set":{"energy":energiaTotal}})
                        elif((energiaA + energyGain)>100):
                            idPokemonA = teamA[posicionA]["_id"]
                            idPokemonA = ObjectId(idPokemonA)
                            energiaTotal = energiaA + energyGain
                            collTeam1.update_one({"_id":idPokemonA},{"$set":{"energy":100}})
                    
                #ATAQUE CARGADO
                elif(tipoAtaque=="cargado"):

                    #COMPROBAR SI TIENE ENERGIA SUFICIENTE PARA HACER EL ATAQUE CARGADO, SI NO TIENE ENERGIA SUFICIENTE EL ATAQUE FALLARA
                    energyLost = collMoves.find_one({"_id":ChargeA},{"energyCost":1})
                    energyLost = energyLost["energyCost"]
                    energiaA = teamA[posicionA]["energy"]

                    energyLost = int(energyLost)


                    if(energyLost<=energiaA):
                        pprint("Ataque cargado")
                        
                        #PODER DEL ATAQUE SELECCIONADO
                        poder = collMoves.find_one({"_id":ChargeA},{"pwr":1})
                        #TIPO DEL ATAQUE SELECCIONADO
                        tipo = collMoves.find_one({"_id":ChargeA},{"type":1})
                        poder = int(poder["pwr"])
                        ataque = int(atkA)
                        defensa = int(defensaB)
                        dañoTotal = (ataque * poder) / (defensa * 2)

                        pprint("DAÑO TOTAL CAUSADO")
                        pprint(dañoTotal)

                        #SI EL ATAQUE COINCIDE CON EL TIPO DEL POKEMON ATACANTE (daño x 1,5)
                        for t in tipoA:
                            if(t == tipo["type"]):
                                dañoTotal = dañoTotal * 1.5

                        pprint("DAÑO TOTAL DESPUES DEL TIPO ATACANTE")
                        pprint(dañoTotal)

                        #SI EL ATAQUE COINCIDE CON UNA DE LAS DEBILIDADES DEL POKEMON ATACADO (daño x 2)
                        for dB in debilidadesB:
                            if(dB == tipo["type"]):
                                #CALCULAR EL DAÑO
                                dañoTotal = dañoTotal * 2
                            
                        pprint("DAÑO TOTAL DESPUES DEL WEAKNESS")
                        pprint(dañoTotal)

                        #REDONDEAR EL DAÑO PARA QUE NO TENGA DECIMALES
                        dañoTotal = int(dañoTotal)

                        pprint("DAÑO REDONDEADO SIN DECIMALES")
                        pprint(dañoTotal)

                        if(dañoTotal<teamB[posicionB]["HP"]):
                            hp = teamB[posicionB]["HP"]
                            hp = hp - dañoTotal
                            pprint("HP RESTANTE DEL POKEMON B")
                            pprint(hp)

                            #SE ACTUALIZA LA VIDA
                            idPDebilitado = teamB[posicionB]["_id"]
                            idPDebilitado = ObjectId(idPDebilitado)
                                                       
                            #ACTUALIZA EL PRIMER POKEMON DE LA LISTA DEL EQUIPO B (EL POKEMON QUE ESTA COMBATIENDO)
                            collTeam2.update_one({"_id":idPDebilitado},{"$set":{"HP":hp}})

                            #RESTAR LA ENERGIA DEL POKEMON ATACANTE
                            energyLost = collMoves.find_one({"_id":ChargeA},{"energyCost":1})
                            energyLost = energyLost["energyCost"]
                            energiaA = teamA[posicionA]["energy"]
                            
                            energyLost = int(energyLost)

                            if((energiaA - energyLost)>=0):
                                idPokemonA = teamA[posicionA]["_id"]
                                idPokemonA = ObjectId(idPokemonA)
                                energiaTotal = energiaA - energyLost
                                collTeam1.update_one({"_id":idPokemonA},{"$set":{"energy":energiaTotal}})
                            elif((energiaA - energyLost)<0):
                                collTeam1.update_one({"_id":idPokemonA},{"$set":{"energy":0}})

                        else:
                            idPDebilitado = teamB[posicionB]["_id"]
                            idPDebilitado = ObjectId(idPDebilitado)
                            pprint(teamB[posicionB]["name"] + " ha sido debilitado, ya no puede combatir.")
                            collTeam2.delete_one({"_id":idPDebilitado})

                            #MIRAR CANTIDA DE POKEMONS DEL EQUIPO A
                            consultarPokemons = collTeam2.find()

                            if(consultarPokemons.count()==0):
                                pprint("El combate ha finalizado, ha ganado el equipo A.")
                                break

                            #EL EQUIPO B CAMBIA AL SIGUIENTE POKEMON
                            posicionB = 0

                            #RESTAR LA ENERGIA DEL POKEMON ATACANTE
                            energyLost = collMoves.find_one({"_id":ChargeA},{"energyCost":1})
                            energyLost = energyLost["energyCost"]
                            energiaA = teamA[posicionA]["energy"]

                            energyLost = int(energyLost)

                            if((energiaA - energyLost)>=0):
                                idPokemonA = teamA[posicionA]["_id"]
                                idPokemonA = ObjectId(idPokemonA)
                                energiaTotal = energiaA - energyLost
                                collTeam1.update_one({"_id":idPokemonA},{"$set":{"energy":energiaTotal}})
                            elif((energiaA - energyLost)<0):
                                collTeam1.update_one({"_id":idPokemonA},{"$set":{"energy":0}})

                    #SI NO TIENE ENERGIA SUFICIENTE EL ATAQUE FALLA
                    else:
                        pprint("El ataque ha fallado")

            else:
                pprint("Cambiar Pokémon")
                pokemons = collTeam2.find()
                pokemons = pokemons.count()
                pokemons = str(pokemons-1)
                print("Estas en la posicion "+str(posicionA))
                print("Escribe el numero de la posicion del pokemon por el que quieres cambiar del 0 al "+ pokemons + ".")
                accion = input("Acción: ")
                posicionA=accion
                posicionA = int(posicionA)
                
                hpA = teamA[posicionA]["HP"]
                #CANTIDAD DE ENERGIA
                energiaA = teamA[posicionA]["energy"]

                #CANTIDAD DE DEFENSA
                defensaA = teamA[posicionA]["def"]

                #ID DE LOS ATAQUES RAPIDOS
                FastA = teamA[posicionA]["moves"]["fast"]
                
                ChargeA = teamA[posicionA]["moves"]["charged"]
                  
                #CANTIDAD DE ATAQUE
                atkA = teamA[posicionA]["atk"]
                
                #DEBILIDADES DEL PRIMER POKEMON A COMBATIR
                debilidadesA = teamA[posicionA]["weaknesses"]
                       
                #TIPOS DEL PRIMER POKEMON A COMBATIR
                tipoA = teamA[posicionA]["type"]
                
                
                pprint("Has seleccionado el Pokémon " + teamA[posicionA]["name"])      
                pprint("Información del Pokémon seleccionado: ")
                pprint(teamA[posicionA])


            #TURNO DEL EQUIPO B
            print("Equipo B quieres atacar o cambiar? (Escribe 'atacar' o 'cambiar')")
            accion = input("Acción: ")
            accion = accion.lower()
            if(accion=="atacar"):
                tipoAtaque = input("Ataque rápido o Ataque cargado: (Escribe 'rapido' o 'cargado')")
                tipoAtaque = tipoAtaque.lower() 

                if(tipoAtaque=="rapido"):
                    pprint("Ataque rápido")

                    #PODER DEL ATAQUE SELECCIONADO
                    poder = collMoves.find_one({"_id":FastB},{"pwr":1})
                    tipo = collMoves.find_one({"_id":FastB},{"type":1})
                    poder = int(poder["pwr"])
                    ataque = int(atkB)
                    defensa = int(defensaA)
                    dañoTotal = (ataque * poder) / (defensa * 2)

                    pprint("DAÑO TOTAL CAUSADO")
                    pprint(dañoTotal)

                    #SI EL ATAQUE COINCIDE CON EL TIPO DEL POKEMON ATACANTE (daño x 1,5)
                    for t in tipoB:
                        if(t == tipo["type"]):
                            dañoTotal = dañoTotal * 1.5

                    pprint("DAÑO TOTAL DESPUES DEL TIPO ATACANTE")
                    pprint(dañoTotal)

                    #SI EL ATAQUE COINCIDE CON UNA DE LAS DEBILIDADES DEL POKEMON ATACADO (daño x 2)
                    for dA in debilidadesA:
                        if(dA == tipo["type"]):
                            #CALCULAR EL DAÑO
                            dañoTotal = dañoTotal * 2
                        
                    pprint("DAÑO TOTAL DESPUES DEL WEAKNESS")
                    pprint(dañoTotal)

                    #REDONDEAR EL DAÑO PARA QUE NO TENGA DECIMALES
                    dañoTotal = int(round(dañoTotal))

                    pprint("DAÑO REDONDEADO SIN DECIMALES")
                    pprint(dañoTotal)

                    if(dañoTotal<teamA[posicionA]["HP"]):
                        hp = teamA[posicionA]["HP"]
                        hp = hp - dañoTotal
                        pprint("HP RESTANTE DEL POKEMON A")
                        pprint(hp)  

                        #SE ACTUALIZA LA VIDA
                        idPDebilitado = teamA[posicionA]["_id"]
                        idPDebilitado = ObjectId(idPDebilitado)
                        
                        #ACTUALIZA EL PRIMER POKEMON DE LA LISTA DEL EQUIPO A (EL POKEMON QUE ESTA COMBATIENDO)
                        collTeam1.update_one({"_id":idPDebilitado},{"$set":{"HP":hp}})

                        #AUGMENTA LA ENERGIA DEL POKEMON ATACANTE
                        energyGain = collMoves.find_one({"_id":FastB},{"energyGain":1})
                        energyGain = energyGain["energyGain"]
                        energiaB = teamB[posicionB]["energy"]

                        energyGain = int(energyGain)

                        if((energiaB + energyGain)<100):
                            idPokemonB = teamB[posicionB]["_id"]
                            idPokemonB = ObjectId(idPokemonB)
                            energiaTotal = energiaB + energyGain
                            collTeam2.update_one({"_id":idPokemonB},{"$set":{"energy":energiaTotal}})
                        elif((energiaB + energyGain)>100):
                            collTeam2.update_one({"_id":idPokemonB},{"$set":{"energy":100}})
                        

                    else:
                        idPDebilitado = teamA[posicionA]["_id"]
                        idPDebilitado = ObjectId(idPDebilitado)
                        pprint(teamA[posicionA]["name"] + " ha sido debilitado, ya no puede combatir.")
                        collTeam1.delete_one({"_id":idPDebilitado})

                        #MIRAR CANTIDA DE POKEMONS DEL EQUIPO A
                        consultarPokemons = collTeam1.find()

                        if(consultarPokemons.count()==0):
                                pprint("El combate ha finalizado, ha ganado el equipo B.")
                                break

                        #EL EQUIPO A CAMBIA AL SIGUIENTE POKEMON (EL PRIMER POKEMON DE LA LISTA)
                        posicionA = 0

                        #AUGMENTA LA ENERGIA DEL POKEMON ATACANTE
                        energyGain = collMoves.find_one({"_id":FastB},{"energyGain":1})
                        energyGain = energyGain["energyGain"]
                        energiaB = teamB[posicionB]["energy"]

                        energyGain = int(energyGain)

                        if((energiaB + energyGain)<100):
                            idPokemonB = teamB[posicionB]["_id"]
                            idPokemonB = ObjectId(idPokemonB)
                            energiaTotal = energiaB + energyGain
                            collTeam2.update_one({"_id":idPokemonB},{"$set":{"energy":energiaTotal}})
                        elif((energiaB + energyGain)>100):
                            collTeam2.update_one({"_id":idPokemonB},{"$set":{"energy":100}})

                #ATAQUE CARGADO
                elif(tipoAtaque=="cargado"):
                    
                   #COMPROBAR SI TIENE ENERGIA SUFICIENTE PARA HACER EL ATAQUE CARGADO, SI NO TIENE ENERGIA SUFICIENTE EL ATAQUE FALLARA
                    energyLost = collMoves.find_one({"_id":ChargeB},{"energyCost":1})
                    energyLost = energyLost["energyCost"]
                    energiaB = teamB[posicionB]["energy"] 

                    energyLost = int(energyLost)

                    if(energyLost<=energiaB):
                        pprint("Ataque cargado")
                        
                        #PODER DEL ATAQUE SELECCIONADO
                        poder = collMoves.find_one({"_id":ChargeB},{"pwr":1})
                        #TIPO DEL ATAQUE SELECCIONADO
                        tipo = collMoves.find_one({"_id":ChargeB},{"type":1})
                        poder = int(poder["pwr"])
                        ataque = int(atkA)
                        defensa = int(defensaB)
                        dañoTotal = (ataque * poder) / (defensa * 2)

                        pprint("DAÑO TOTAL CAUSADO")
                        pprint(dañoTotal)

                        #SI EL ATAQUE COINCIDE CON EL TIPO DEL POKEMON ATACANTE (daño x 1,5)
                        for t in tipoB:
                            if(t == tipo["type"]):
                                dañoTotal = dañoTotal * 1.5

                        pprint("DAÑO TOTAL DESPUES DEL TIPO ATACANTE")
                        pprint(dañoTotal)

                        #SI EL ATAQUE COINCIDE CON UNA DE LAS DEBILIDADES DEL POKEMON ATACADO (daño x 2)
                        for dA in debilidadesA:
                            if(dA == tipo["type"]):
                                #CALCULAR EL DAÑO
                                dañoTotal = dañoTotal * 2
                            
                        pprint("DAÑO TOTAL DESPUES DEL WEAKNESS")
                        pprint(dañoTotal)

                        #REDONDEAR EL DAÑO PARA QUE NO TENGA DECIMALES
                        dañoTotal = int(dañoTotal)

                        pprint("DAÑO REDONDEADO SIN DECIMALES")
                        pprint(dañoTotal)

                        if(dañoTotal<teamA[posicionA]["HP"]):
                            hp = teamA[posicionA]["HP"]
                            hp = hp - dañoTotal
                            pprint("HP RESTANTE DEL POKEMON B")
                            pprint(hp)

                            #SE ACTUALIZA LA VIDA
                            idPDebilitado = teamA[posicionA]["_id"]
                            idPDebilitado = ObjectId(idPDebilitado)
                           
                            #ACTUALIZA EL PRIMER POKEMON DE LA LISTA DEL EQUIPO A (EL POKEMON QUE ESTA COMBATIENDO)
                            collTeam1.update_one({"_id":idPDebilitado},{"$set":{"HP":hp}})
                            
                            #RESTAR LA ENERGIA DEL POKEMON ATACANTE
                            energyLost = collMoves.find_one({"_id":ChargeB},{"energyCost":1})
                            energyLost = energyLost["energyCost"]
                            energiaB = teamB[posicionB]["energy"]

                            energyLost = int(energyLost)

                            if((energiaB - energyLost)>=0):
                                idPokemonB = teamB[posicionB]["_id"]
                                idPokemonB = ObjectId(idPokemonB)
                                energiaTotal = energiaB - energyLost
                                collTeam2.update_one({"_id":idPokemonB},{"$set":{"energy":energiaTotal}})
                            elif((energiaB - energyLost)<0):
                                collTeam2.update_one({"_id":idPokemonB},{"$set":{"energy":0}})

                        else:
                            idPDebilitado = teamA[posicionA]["_id"]
                            idPDebilitado = ObjectId(idPDebilitado)
                            pprint(teamA[posicionA]["name"] + " ha sido debilitado, ya no puede combatir.")
                            collTeam1.delete_one({"_id":idPDebilitado})

                            #MIRAR CANTIDA DE POKEMONS DEL EQUIPO A
                            consultarPokemons = collTeam1.find()

                            if(consultarPokemons.count()==0):
                                pprint("El combate ha finalizado, ha ganado el equipo B.")
                                break


                            #EL EQUIPO B CAMBIA AL SIGUIENTE POKEMON
                            posicionA = 0

                            #RESTAR LA ENERGIA DEL POKEMON ATACANTE
                            energyLost = collMoves.find_one({"_id":ChargeB},{"energyCost":1})
                            energyLost = energyLost["energyCost"]
                            energiaB = teamB[posicionB]["energy"]

                            if((energiaB - energyLost)>=0):
                                idPokemonB = teamB[posicionB]["_id"]
                                idPokemonB = ObjectId(idPokemonB)
                                energiaTotal = energiaB - energyLost
                                collTeam2.update_one({"_id":idPokemonB},{"$set":{"energy":energiaTotal}})
                            elif((energiaB - energyLost)<0):
                                collTeam2.update_one({"_id":idPokemonB},{"$set":{"energy":0}})
                                
                    #SI NO TIENE ENERGIA SUFICIENTE EL ATAQUE FALLA
                    else:
                        pprint("El ataque ha fallado")

            else:
                pprint("Cambiar Pokémon")
                print("Estas en la posicion "+str(posicionB))
                pokemons = collTeam2.find()
                pokemons = pokemons.count()
                pokemons = str(pokemons-1)
                print("Escribe el numero de la posicion del pokemon por el que quieres cambiar del 0 al " + pokemons +".")
                accion = input("Acción: ")
                posicionB=accion
                posicionB = int(posicionB)
                hpB = teamB[posicionB]["HP"]
                #CANTIDAD DE ENERGIA
                energiaB = teamB[posicionB]["energy"]

                #CANTIDAD DE DEFENSA
                defensaB = teamB[posicionB]["def"]

                #ID DE LOS ATAQUES RAPIDOS
                FastB = teamB[posicionB]["moves"]["fast"]
               
                ChargeB = teamB[posicionB]["moves"]["charged"]
                  
                #CANTIDAD DE ATAQUE
                atkB = teamB[posicionB]["atk"]
                
                #DEBILIDADES DEL PRIMER POKEMON A COMBATIR
                debilidadesB = teamB[posicionB]["weaknesses"]
                        
                #TIPOS DEL PRIMER POKEMON A COMBATIR
                tipoB = teamB[posicionB]["type"]
                

                pprint("Has seleccionado el Pokémon " + teamB[posicionB]["name"])      
                pprint("Información del Pokémon seleccionado: ")
                pprint(teamB[posicionB])        

    
    def ayuda():

        return "Comandos disponibles: \n search nombrepokemon propiedad \n release nombrepokemon/id  \n delete nombrepokemon/id \n training nombrepokemon/id"
    def catchrandoms():
        #Genero 12 numeros random aleatorios
        lista=random.sample(range(1, collPokemon.find().count()), 12)
        i=0
        #Team A
        while(i<6):
            consultaPokemon = collPokemon.find_one({"id":int(lista[i])})
            consultaPokemon2=collPokemon.find_one({ "id":int(lista[i]),"candy_count" :{"$exists":True}})
            #GENERAR CAMPOS ALEATORIOS DE HPmax, ATK, DEF
            hpMax = randrange(200,1000)
            atk = randrange(10,50)
            defensa = randrange(10,50)
            CP = hpMax + atk + defensa
            #SELECCIONAR DOS MOVIMIENTOS RANDOM
            movimientos = collMoves.find({"energyGain":{"$exists":True}})
            movimientos2 = collMoves.find({"energyCost":{"$exists":True}})
            movimiento1 = movimientos[randrange(1,movimientos.count())]
            movimiento2 = movimientos2[randrange(1,movimientos2.count())]
            catch_date = datetime.now()
            #hp es igual que hpMax
            hp=hpMax
            if (consultaPokemon2):
                    #AÑADIR TODOS LOS DATOS AL EQUIPO (HACER EL INSERT)
                    pokkemon ={"id":consultaPokemon["id"],"num":consultaPokemon["num"],
                    "name":consultaPokemon["name"],"type":consultaPokemon["type"],
                    "candy":consultaPokemon["candy"],"candy_count":consultaPokemon["candy_count"],
                    "weaknesses":consultaPokemon["weaknesses"],"catch_date":catch_date,
                    "CP":CP,"HPmax":hpMax,"HP":hp,"atk":atk,"def":defensa,"energy":0,
                    "moves":{"fast":movimiento1["_id"],"charged":movimiento2["_id"]},"current_candy":0}

            else:
                    pokkemon ={"id":consultaPokemon["id"],"num":consultaPokemon["num"],
                    "name":consultaPokemon["name"],"type":consultaPokemon["type"],
                    "candy":consultaPokemon["candy"],
                    "weaknesses":consultaPokemon["weaknesses"],"catch_date":catch_date,
                    "CP":CP,"HPmax":hpMax,"HP":hp,"atk":atk,"def":defensa,"energy":0,
                    "moves":{"fast":movimiento1["_id"],"charged":movimiento2["_id"]}}
            
            collTeam1.insert_one(pokkemon)
            i+=1
        #Team B
        while (i<12):
            consultaPokemon = collPokemon.find_one({"id":int(lista[i])})
            consultaPokemon2=collPokemon.find_one({ "id":int(lista[i]),"candy_count" :{"$exists":True}})
            #GENERAR CAMPOS ALEATORIOS DE HPmax, ATK, DEF
            hpMax = randrange(200,1000)
            atk = randrange(10,50)
            defensa = randrange(10,50)
            CP = hpMax + atk + defensa
            #SELECCIONAR DOS MOVIMIENTOS RANDOM
            movimientos = collMoves.find({"energyGain":{"$exists":True}})
            movimientos2 = collMoves.find({"energyCost":{"$exists":True}})
            movimiento1 = movimientos[randrange(1,movimientos.count())]
            movimiento2 = movimientos2[randrange(1,movimientos2.count())]
            catch_date = datetime.now()
            #hp es igual que hpMax
            hp=hpMax
            if (consultaPokemon2):
                    #AÑADIR TODOS LOS DATOS AL EQUIPO (HACER EL INSERT)
                    pokkemon ={"id":consultaPokemon["id"],"num":consultaPokemon["num"],
                    "name":consultaPokemon["name"],"type":consultaPokemon["type"],
                    "candy":consultaPokemon["candy"],"candy_count":consultaPokemon["candy_count"],
                    "weaknesses":consultaPokemon["weaknesses"],"catch_date":catch_date,
                    "CP":CP,"HPmax":hpMax,"HP":hp,"atk":atk,"def":defensa,"energy":0,
                    "moves":{"fast":movimiento1["_id"],"charged":movimiento2["_id"]},"current_candy":0}

            else:
                    pokkemon ={"id":consultaPokemon["id"],"num":consultaPokemon["num"],
                    "name":consultaPokemon["name"],"type":consultaPokemon["type"],
                    "candy":consultaPokemon["candy"],
                    "weaknesses":consultaPokemon["weaknesses"],"catch_date":catch_date,
                    "CP":CP,"HPmax":hpMax,"HP":hp,"atk":atk,"def":defensa,"energy":0,
                    "moves":{"fast":movimiento1["_id"],"charged":movimiento2["_id"]}}
            
            collTeam2.insert_one(pokkemon)
            i+=1
        pprint("Pokemons random de los dos equipos generados")        
    #Bucle del programa principal
    pprint("Introduce un comando de este estilo: Search Bulbasur Height, Release 001 o Release Bulbasur, para salir solo pon exit")
    while (True):
        variable = input("Indica el comando: ")
        variable = variable.split()
        #Con capitalize impedimos que al escribir el comando y el usuario meta por ejemplo bulbasaur bien escrito, consiga encontrarlo igualmente
        if (variable[0].lower() == "search"):
            #Si la variable la devuelve nula quiere decir
            variable[1]=variable[1].capitalize() 
            consulta=search(variable)
            if(consulta==None):
                pprint("El pokemon no existe o esta mal escrito")
            else:
                pprint(consulta)
        elif (variable[0].lower() == "release"):
            variable[1]=variable[1].capitalize()
            release(variable)
        elif (variable[0].lower() == "training"):
            variable[1]=variable[1].capitalize()
            training(variable)
        elif (variable[0].lower() == "catch"):
            variable[1]=variable[1].capitalize()
            catch(variable)
        elif (variable[0].lower() == "combat"):
            pprint(combat())
        elif (variable[0].lower() == "--help"):
            pprint(ayuda())
        elif (variable[0].lower() == "exit"):
            pprint("Saliendo del programa...")
            break
        else:
            pprint("Comando no valido, porfavor para saber los comandos disponibles escriba --help")


    '''  No se ha usado el switch ya que da problemas a la hora de pasar la sentencia, tal cual esta programado en python
    def switch(argument):
        # Para hacerlo mas estructurado, pasaremos la sentencia despues
        sentencia=argument.split()
        argument=argument.split()
        argument=argument[0]
        switcher = {
            "--help": ayuda(),
            "search": search(sentencia),
            "release": release(sentencia),
            "delete": delete(sentencia),
            "training": training(sentencia),
            "catch": catch(sentencia),
            "combat": combat(),
            "exit" : "exit"
            
        }
        return switcher.get(argument,"Comando no valido, porfavor para saber los comandos disponibles escriba --help")
    '''
