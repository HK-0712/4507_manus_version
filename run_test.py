import os
import subprocess
import time

# Read test commands from file
with open('test_script_v2.txt', 'r', encoding='utf-8') as f:
    commands = f.readlines()

# Remove empty lines and strip whitespace
commands = [cmd.strip() for cmd in commands if cmd.strip()]

print("Starting automated test of TestEnsemble...")
print("=" * 60)

# Start Java program as a subprocess
process = subprocess.Popen(
    ['java', 'TestEnsemble'],
    stdin=subprocess.PIPE,
    stdout=subprocess.PIPE,
    stderr=subprocess.PIPE,
    text=True,
    bufsize=1
)

# Send each command to the Java program
for i, cmd in enumerate(commands):
    print(f"\n>>> Sending command {i+1}/{len(commands)}: {cmd}")
    process.stdin.write(cmd + '\n')
    process.stdin.flush()
    time.sleep(0.1)  # Small delay to allow output to be generated

# Close stdin to signal end of input
process.stdin.close()

# Wait for process to complete and get output
stdout, stderr = process.communicate(timeout=10)

# Print all output
print("\n" + "=" * 60)
print("PROGRAM OUTPUT:")
print("=" * 60)
print(stdout)

if stderr:
    print("\n" + "=" * 60)
    print("ERRORS (if any):")
    print("=" * 60)
    print(stderr)

print("\n" + "=" * 60)
print("Test completed!")
print("=" * 60)
