# this is my first VN, but I'll just play with dialogue for now

init python:
    # Simply point to your font file
    style.default.font = "game\fonts\ja-jp.ttf"

# declare characters here
define mc = Character("Laila")
define rando = Character("Rando")
image laila = "images/laila.jpg"

# game starts here

label start:
    show laila at left

    mc "Hello! This is not a character I drew but her name is spelled like mine in Genshin Impact ES"

    menu:
        "What should I say next?"

        "I'm excited to learn!":
            rando "Didn't you say the same thing about C?"
            mc "Yes but that's different! I didn't know pointers would be such a huge learning curve!"
        
        "This was much easier to start than I initially thought":
            rando "See how starting is the hardest step? Now you just have to continue"

    mc "Now I just need to pick up my iPad and start drawing character sprites"

    rando "If you hadn't combined all the layers of your character drawings, you could've had character sprites already"

    mc "... leave me alone..."

    return