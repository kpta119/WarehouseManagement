import os

def export_text(file_path):
    with open(file_path, 'r', encoding="utf-8") as file:
        content = file.read()
    return content
    
def export_files_in_directory(directory_path):
    result = ""
    for root, _, files in os.walk(directory_path):
        for file in files:
            file_path = os.path.join(root, file)
            if file.endswith('.jsx'):
                try:
                    base_path = r"C:\Users\Adrian Garbowski\Desktop\Programs\College\BD2\frontend\src"
                    result += f"// {file_path.replace(base_path, '')}\n\n"
                    file_text = export_text(file_path)
                    result += f"{file_text}\n\n"
                except Exception as e:
                    print(f"Error reading {file_path}: {e}")
    return result

if __name__ == "__main__":
    directory_path = os.path.join(os.getcwd(), 'src')
    total_text = export_files_in_directory(directory_path)
    with open('exported_text.txt', 'w', encoding="utf-8") as output_file:
        output_file.write(total_text)
    print(f"Exported text from all files to exported_text.txt")
