import os

def count_lines_in_file(file_path):
    with open(file_path, 'r', encoding="utf-8") as file:
        return sum(1 for _ in file)
    
def count_lines_in_directory(directory_path):
    total_lines = 0
    total_files = 0
    for root, _, files in os.walk(directory_path):
        for file in files:
            file_path = os.path.join(root, file)
            if file.endswith('.jsx'):
                try:
                    lines = count_lines_in_file(file_path)
                    total_lines += lines
                    total_files += 1
                except Exception as e:
                    print(f"Error reading {file_path}: {e}")
    return total_lines, total_files

if __name__ == "__main__":
    directory_path = os.path.join(os.getcwd(), 'src')
    total_lines, total_files = count_lines_in_directory(directory_path)
    print(f"Total lines in all files: {total_lines}")
    print(f"Total jsx files: {total_files}")
