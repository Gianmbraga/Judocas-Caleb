import os

def generate_maven_dependencies(lib_folder):
    dependencies = []
    for root, _, files in os.walk(lib_folder):
        for file in files:
            if file.endswith(".jar"):
                jar_path = os.path.relpath(os.path.join(root, file), lib_folder)
                group_id = "local.libs"  # Placeholder group ID
                artifact_id = os.path.splitext(file)[0]  # Use the JAR filename as artifact ID
                version = "1.0"  # Default version
                dependency = f"""
    <dependency>
        <groupId>{group_id}</groupId>
        <artifactId>{artifact_id}</artifactId>
        <version>{version}</version>
        <scope>system</scope>
        <systemPath>${{project.basedir}}/{lib_folder}/{jar_path}</systemPath>
    </dependency>"""
                dependencies.append(dependency)

    return "\n".join(dependencies)

def main():
    lib_folder = input("Enter the path to the library folder (e.g., 'lib'): ").strip()
    if not os.path.exists(lib_folder):
        print(f"Error: Folder '{lib_folder}' does not exist.")
        return
    
    dependencies = generate_maven_dependencies(lib_folder)
    if not dependencies:
        print(f"No .jar files found in '{lib_folder}'.")
        return

    print("Generated Maven dependencies:")
    print(dependencies)

    with open("generated_dependencies.xml", "w") as file:
        file.write(dependencies)

    print("\nDependencies have been saved to 'generated_dependencies.xml'.")
    print("You can copy and paste them into your pom.xml.")

if __name__ == "__main__":
    main()
