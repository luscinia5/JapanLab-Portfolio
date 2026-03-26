import random
import sys
from typing import Optional

class pyGuessingGame:
    # This is the main game class. It's kind of like Java

    def __init__(self, min_num: int = 1, max_num: int = 100, max_attempts: Optional[int] = None):
        # it's like Java's constructor
        self.min_num = min_num
        self.max_num = max_num
        self.secret_number = random.randint(min_num, max_num)
        self.attempts = 0
        self.max_attempts = max_attempts or (max_num - min_num) // 5
    
    def get_player_guess(self) -> int:
        # get and validate player guess
        while True:
            try:
                guess = input(f"Guess a number ({self.min_num}-{self.max_num}): ")
                guess = int(guess)

                if not (self.min_num <= guess <= self.max_num):
                    print(f"Please enter a number between {self.min_num} and {self.max_num}")
                    continue
                return guess
            
            except ValueError:
                print("That's not a valid number. Please try again")

    def check_guess(self, guess: int) -> tuple[bool, str]:
        # returns (is_correct, hint message)
        self.attempts += 1

        if guess < self.secret_number:
            return False, "Too low!"
        elif guess > self.secret_number:
            return False, "Too high!"
        else:
            return True, f"Correct! You got it in {self.attempts} attempts!"
        
    def play(self) -> None:
        # main game loop
        print(f"\n Welcome to this simple number guessing game!")
        print(f"I'm thinking of a number between {self.min_num} and {self.max_num}")
        print(f"You have {self.max_attempts} attempts to guess it!\n")

        while self.attempts < self.max_attempts:
            guess = self.get_player_guess()
            is_correct, message = self.check_guess(guess)
            print(message)

            if is_correct: 
                return
        print(f"\nYou ran out of attempts 💀💀. The number was {self.secret_number}")

# main() outside the class - it shouldn't be a class method
def main():
    # entry point
    try:
        game = pyGuessingGame(min_num=1, max_num=100, max_attempts=10)
        game.play()

        # ask if they want to play again
        while True:
            play_again = input("\nPlay again? (y/n): ").lower()
            if play_again in ['y', 'yes']:
                main()  # This calls the main function, not the class
                break
            elif play_again in ['n', 'no']:
                print("Thanks for playing. Bye!!")
                sys.exit(0)
            else:
                print("Please enter 'y' or 'n'")
    except KeyboardInterrupt:
        print("\nGame interrupted. Goodbye!")
        sys.exit(0)
    except Exception as e:
        print(f"An unexpected error occurred: {e}")
        sys.exit(1)

# MOVED this outside the class - it's a module-level check
if __name__ == "__main__":
    main()