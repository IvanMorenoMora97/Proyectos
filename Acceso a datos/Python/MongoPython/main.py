import pymongo
from pymongo import MongoClient
from mongoengine import *
import datetime
import random
from pprint import pprint
from datetime import date
from datetime import datetime
from random import randrange, uniform
import pokemon
import programa
conexion = connect('project')
cluster = MongoClient()
db = cluster["project"]
collPokemon = db["pokemon"]
collMoves = db['moves']
collTeam = db['team']
collTeam1=db["team1"]
collTeam2=db["team2"]
#Drops de datos
collPokemon.drop()
collMoves.drop()
collTeam.drop()
collTeam1.drop()
collTeam2.drop()
#Como un enum en java nos permitira fijar los campos 
TYPES = ('Bug','Dark','Dragon','Electric','Fairy','Fight','Fire','Flying','Ghost','Grass','Ground','Ice','Normal','Poison','Psychic','Rock','Steel','Water')

#Tiene que estar obligatoriamente antes de Pokemon. Python carga por orden
class Evolution(EmbeddedDocument):
    num = StringField()
    name = StringField()
class Pokemon(Document):
    #db_field busca el nombre en mongo, si es que es distinto a como lo tienes en el nombre de la clase
    pid = FloatField(required = True, db_field='id')
    num = StringField(required = True)
    name = StringField(required = True)
    img = StringField(required = True)
    #type es lista de strings. Pero tienen que ser aquellas definidas en la tupla TYPES (funciona como un enum)
    type = ListField(choice = TYPES, required = True)
    height = StringField(required = True)
    weight = StringField(required = True)
    candy = StringField(required = True)
    candy_count = IntField(min_value = 0, max_value = 500)
    egg = StringField(required = True)
    spawn_chance = FloatField(required = True)
    avg_spawns = FloatField(required = True)
    spawn_time = StringField(required = True)
    multipliers = ListField(FloatField(),required = True)
    weaknesses = ListField(choice = TYPES,required = True)
    #next_evolutione s una lista de Evolution, que es el documento incrustado que acabamos de crear
    next_evolution = ListField(EmbeddedDocumentField('Evolution'))
    prev_evolution = ListField(EmbeddedDocumentField('Evolution'))
class Moves(Document):
    meta={'allow_inheritance':True} 
    name= StringField(required = True)
    pwr= FloatField(required=True)
    type=StringField(choice=TYPES,required=True)
class FastMove(Moves):
    energyGain=DecimalField(required=True,min_value = 0, max_value = 20)
class ChargedMove(Moves):
    energyCost=IntField(required=True,min_value = 33, max_value = 100)
class MovesP(EmbeddedDocument):
    fast=ObjectIdField(required=True)
    charged=ObjectIdField(required=True)
class Team(Document):

    pid = FloatField(required = True, db_field='id')
    num = StringField(required = True)
    name = StringField(required = True)
    #type es lista de strings. Pero tienen que ser aquellas definidas en la tupla TYPES (funciona como un enum)
    type = ListField(choice = TYPES, required = True)
    catch_date = DateField()
    CP=IntField(required=True,min_value=0,max_value=10000)
    HPmax=IntField(required=True,min_value=20,max_value=1000)
    HP=IntField(required=True,min_value=20,max_value=1000)
    atk=IntField(required=True,min_value=10,max_value=50)
    defense=IntField(required=True,min_value=10,max_value=50,db_field='def')
    energy=IntField(required=True,min_value=0,max_value=100)
    moves=EmbeddedDocumentField('MovesP')
    candy = StringField(required = True)
    candy_count = IntField(min_value = 0, max_value = 500)
    current_candy=IntField(min_value = 0, max_value = 500)
    weaknesses = ListField(choice = TYPES,required = True)
class Team1(Document):

    pid = FloatField(required = True, db_field='id')
    num = StringField(required = True)
    name = StringField(required = True)
    #type es lista de strings. Pero tienen que ser aquellas definidas en la tupla TYPES (funciona como un enum)
    type = ListField(choice = TYPES, required = True)
    catch_date = DateField()
    CP=IntField(required=True,min_value=0,max_value=10000)
    HPmax=IntField(required=True,min_value=20,max_value=1000)
    HP=IntField(required=True,min_value=20,max_value=1000)
    atk=IntField(required=True,min_value=10,max_value=50)
    defense=IntField(required=True,min_value=10,max_value=50,db_field='def')
    energy=IntField(required=True,min_value=0,max_value=100)
    moves=EmbeddedDocumentField('MovesP')
    candy = StringField(required = True)
    candy_count = IntField(min_value = 0, max_value = 500)
    current_candy=IntField(min_value = 0, max_value = 500)
    weaknesses = ListField(choice = TYPES,required = True)
class Team2(Document):

    pid = FloatField(required = True, db_field='id')
    num = StringField(required = True)
    name = StringField(required = True)
    #type es lista de strings. Pero tienen que ser aquellas definidas en la tupla TYPES (funciona como un enum)
    type = ListField(choice = TYPES, required = True)
    catch_date = DateField()
    CP=IntField(required=True,min_value=0,max_value=10000)
    HPmax=IntField(required=True,min_value=20,max_value=1000)
    HP=IntField(required=True,min_value=20,max_value=1000)
    atk=IntField(required=True,min_value=10,max_value=50)
    defense=IntField(required=True,min_value=10,max_value=50,db_field='def')
    energy=IntField(required=True,min_value=0,max_value=100)
    moves=EmbeddedDocumentField('MovesP')
    candy = StringField(required = True)
    candy_count = IntField(min_value = 0, max_value = 500)
    current_candy=IntField(min_value = 0, max_value = 500)
    weaknesses = ListField(choice = TYPES,required = True)
'''Pokemon'''
#Script para crear los pokemons, modulo creado en otro punto py
pokemons=pokemon.pokemon()
collPokemon.insert_many(pokemons)
''' Ataques '''
Chikorita = Pokemon(pid = 152.0,num = "152",name = "Chikorita",img = "a.com",type = ["Grass"],height = "0.9 m",weight = "6.4 kg",candy = "Chikorita Candy",candy_count = 25.0,egg = "2 Km",spawn_chance = 0.69,avg_spawns = 69.0,spawn_time = "20:00",multipliers = [1.58],
    weaknesses = ["Fire","Ice","Flying","Bug"],
    #next_evolutione s una lista de Evolution, que es el documento incrustado que acabamos de crear
    next_evolution = [ {"num":"153","name":"Bayleaf"}, {"num":"154","name":"Meganium"} ]
).save()


Bayleaf = Pokemon(
    pid = 153.0,
    num = "153",
    name = "Bayleaf",
    img = "a.com",
    type = ["Grass"],
    height = "0.9 m",
    weight = "6.4 kg",
    candy = "Chikorita Candy",
    candy_count = 100.0,
    egg = "2 Km",
    spawn_chance = 0.69,
    avg_spawns = 69.0,
    spawn_time = "20:00",
    multipliers = [1.58],
    weaknesses = ["Fire","Ice","Flying","Bug"],
    #next_evolutione s una lista de Evolution, que es el documento incrustado que acabamos de crear
    prev_evolution=[{"num":"152","name":"Chikorita"}],
    next_evolution = [ {"num":"154","name":"Meganium"} ]
).save()

Meganium = Pokemon(
    pid = 154.0,
    num = "154",
    name = "Meganium",
    img = "a.com",
    type = ["Grass"],
    height = "1.7 m",
    weight = "79.5 kg",
    candy = "Meganium Candy",
    egg = "2 Km",
    spawn_chance = 0.69,
    avg_spawns = 69.0,
    spawn_time = "20:00",
    multipliers = [1.58],
    weaknesses = ["Fire","Ice","Flying","Bug"],
    #next_evolutione s una lista de Evolution, que es el documento incrustado que acabamos de crear
    prev_evolution=[{"num":"153","name":"Bayleaf"},{"num":"152","name":"Chikorita"}]
).save()
Cyndaquil = Pokemon(
    pid = 155.0,
    num = "155",
    name = "Cyndaquil",
    img = "a.com",
    type = ["Fire"],
    height = "0.9 m",
    weight = "7.9 kg",
    candy = "Cyndaquil Candy",
    candy_count = 25.0,
    egg = "2 Km",
    spawn_chance = 0.69,
    avg_spawns = 69.0,
    spawn_time = "20:00",
    multipliers = [1.58],
    weaknesses = ["Water","Ground","Rock"],
    #next_evolutione s una lista de Evolution, que es el documento incrustado que acabamos de crear
    next_evolution = [ {"num":"156","name":"Quilava"}, {"num":"157","name":"Typhlosion"} ]
).save()

Quilava = Pokemon(
    pid = 156.0,
    num = "156",
    name = "Quilava",
    img = "a.com",
    type = ["Fire"],
    height = "0.9 m",
    weight = "19.0 kg",
    candy = "Cyndaquil Candy",
    candy_count = 50.0,
    egg = "2 Km",
    spawn_chance = 0.69,
    avg_spawns = 69.0,
    spawn_time = "20:00",
    multipliers = [1.58],
    weaknesses = ["Water","Ground","Rock"],
    #next_evolutione s una lista de Evolution, que es el documento incrustado que acabamos de crear
    prev_evolution=[{"num":"155","name":"Cyndaquil"}],
    next_evolution = [ {"num":"157","name":"Typhlosion"} ]
).save()

Typhlosion = Pokemon(
    pid = 157.0,
    num = "157",
    name = "Typhlosion",
    img = "a.com",
    type = ["Fire"],
    height = "1.7 m",
    weight = "79.5 kg",
    candy = "Cyndaquil Candy",
    candy_count = 100.0,
    egg = "2 Km",
    spawn_chance = 0.69,
    avg_spawns = 69.0,
    spawn_time = "20:00",
    multipliers = [1.58],
    weaknesses = ["Water","Ground","Rock"],
    #next_evolutione s una lista de Evolution, que es el documento incrustado que acabamos de crear
    prev_evolution=[{"num":"156","name":"Quilava"},{"num":"155","name":"Cyndaquil"}]
).save()
''' Movimientos'''
#Ejemplo de crear un objeto para mongo con python (bastante infeciente si te sabes la sintaxis de mongo, pero te limita los campos)
DragonTail=FastMove(name= "Dragon Tail",pwr= "15",type="Dragon",energyGain="20").save()
OriginPulse=ChargedMove(name= "Origin Pulse",pwr= "130",type="Water",energyCost="100").save()
IronTail=FastMove(name= "Iron Tail",pwr= "15",type="Steel",energyGain="20").save()
DoomDesire=ChargedMove(name= "Doom Desire",pwr= "80",type="Steel",energyCost="100").save()
Waterfall=FastMove(name= "Waterfall",pwr= "16",type="Water",energyGain="20").save()
BraveBird=ChargedMove(name= "Brave Bird",pwr= "90",type="Flying",energyCost="100").save()
RazorLeaf=FastMove(name= "Razor Leaf",pwr= "13",type="Grasse",energyGain="20").save()
Avalanche=ChargedMove(name= "Avalanche",pwr= "90",type="Ice",energyCost="50").save()
#Un pequeño "script" para los movimientos la otra manera de hacerlo (no te marca el _Cls)
movimientos = [
  { "name": "STEEL WING", "pwr": "11","type":"Steel","energyGain":"20"},
  { "name": "Precipice Blades", "pwr": "130","type":"Ground","energyCost":"100"}
]
resultado= collMoves.insert_many(movimientos)

''' Ejemplo de Team'''
Team1=Team(

    pid= 152.0,
    num ="152",
    name = "Chikorita",
    type = ["Grass"],
    catch_date =  datetime.now(),
    CP="100",
    HPmax="100",
    HP="100",
    atk="35",
    defense="24",
    energy="100",
    moves={"fast":"5e877be5c7f41fc1aab42ae1","charged":"5e877be5c7f41fc1aab42ae1"},
    candy = "1",
    candy_count = "200",
    current_candy="2",
    weaknesses = ["Dark"]).save()



#NombreDelaClase.objects es un campo estatico que te deveuvlve una lista con todos los documentos de esa colleciton
for pkmn in Pokemon.objects:
    print("Pokémon: " + pkmn.name + ", Número: " + pkmn.num)
    #pkmn.delete()

programa.programageneral()
