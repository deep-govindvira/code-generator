#include "bits/stdc++.h"
using namespace std;

const string use = "use";
const string helper = "helper";

string lower = "blog";
string given = "Blog"; // first letter capital database
string fsdb = "blog"; // first letter small database
vector<string> fields;

string replacePlaceholders(string result, string target, string replacement) {
    size_t pos = 0;
    while ((pos = result.find(target, pos)) != string::npos) {
        result.replace(pos, target.length(), replacement);
        pos += replacement.length();
    }
    return result;
}

void generatePOM() {
    ifstream input(helper + "/pom.xml");

    stringstream buffer;
    buffer << input.rdbuf(); 

    string contents = buffer.str();

    ofstream output(use + "/pom.xml");
    output << contents;
}

void generateAPI() {
    ifstream input(helper + "/api.yaml");

    stringstream buffer;
    buffer << input.rdbuf(); 

    string contents = buffer.str();

    contents = replacePlaceholders(contents, "llllllllll", lower);
    contents = replacePlaceholders(contents, "GGGGGGGGGG", given);

    ofstream output(use + "/" + given + "-api.yaml");
    output << contents << endl;

    for (auto field : fields) {
        output << "        " << field << ":\n";
        output << "          type: string\n";
    }
}

void generatePLUGIN() {
    ifstream input(helper + "/plugin.xml");

    stringstream buffer;
    buffer << input.rdbuf(); 

    string contents = buffer.str();
    contents = replacePlaceholders(contents, "GGGGGGGGGG", given);

    ofstream output(use + "/" + given + "-plugin.xml");
    output << contents;
}

void generateModelMapper() {
    ifstream input(helper + "/ModelMapperConfig.java");

    stringstream buffer;
    buffer << input.rdbuf(); 

    string contents = buffer.str();

    ofstream output(use + "/ModelMapperConfig.java");
    output << contents;
}

void generateMapperConfig() {
    ifstream input(helper + "/MapperConfig.java");

    stringstream buffer;
    buffer << input.rdbuf(); 

    string contents = buffer.str();
    contents = replacePlaceholders(contents, "SLLLLLLLLL", fsdb);
    contents = replacePlaceholders(contents, "GGGGGGGGGG", given);

    ofstream output(use + "/" + given + "MapperConfig.java");
    output << contents << endl;

    for (auto field : fields) {
        field[0] += ('A' - 'a');
        output << "        ";
        output << fsdb << given << "DTOTypeMap.addMapping(" << given << "::get" << field << ", " << given << "DTO::set" << field << ");\n";
        output << "        ";
        output << fsdb << "DTO" << given << "TypeMap.addMapping(" << given << "DTO::get" << field << ", " << given << "::set" << field << ");\n\n";
    }

    output << "    }\n}";
}

void generateDynamoDBConfig() {
    ifstream input(helper + "/DynamoDBConfig.java");

    stringstream buffer;
    buffer << input.rdbuf(); 

    string contents = buffer.str();

    ofstream output(use + "/DynamoDBConfig.java");
    output << contents;
}

void generateController() {
    ifstream input(helper + "/Controller.java");

    stringstream buffer;
    buffer << input.rdbuf(); 

    string contents = buffer.str();
    contents = replacePlaceholders(contents, "SLLLLLLLLL", fsdb);
    contents = replacePlaceholders(contents, "GGGGGGGGGG", given);

    ofstream output(use + "/" + given + "Controller.java");
    output << contents;
}

void generateEntity() {
    ifstream input(helper + "/Entity.java");

    stringstream buffer;
    buffer << input.rdbuf(); 

    string contents = buffer.str();
    contents = replacePlaceholders(contents, "SLLLLLLLLL", fsdb);
    contents = replacePlaceholders(contents, "GGGGGGGGGG", given);

    ofstream output(use + "/" + given + ".java");
    output << contents << "\n\n";

    for (auto field : fields) {
        output << "        ";
        if (field == "id") output << "@DynamoDBHashKey(attributeName = \"" << field << "\")\n";
        else output << "@DynamoDBAttribute(attributeName = \"" << field << "\")\n";
        output << "        ";
        output << "private String " << field << ";\n\n";
    }

    output << "}";
}

void generateApplicationYaml() {
    ifstream input(helper + "/application.yaml");

    stringstream buffer;
    buffer << input.rdbuf(); 

    string contents = buffer.str();

    ofstream output(use + "/application.yaml");
    output << contents;
}

void generatePostmanAPIS() {
    ifstream input(helper + "/postman.txt");

    stringstream buffer;
    buffer << input.rdbuf(); 

    string contents = buffer.str();
    contents = replacePlaceholders(contents, "llllllllll", lower);

    ofstream output(use + "/postman.txt");
    output << contents << "\n\n{\n";

    int count = 0;

    for (auto field : fields) {
        count++;
        output << "    ";
        output << "\"" << field << "\": " << "\"Hello World\"";
        if (count != fields.size()) output << ",";
        output << "\n";
    }

    output << "}";
}

int main() {
    cout << "Enter Item: ";
    cin >> given;
    
    lower = given;
    transform(lower.begin(), lower.end(), lower.begin(), ::tolower);
    fsdb = given;
    fsdb[0] += 'a' - 'A';
    
    int n;
    cout << "Enter number of fields: ";
    cin >> n;

    fields = vector<string> (n);

    cout << "Enter field names: ";
    for (int i = 0; i < n; ++i)
        cin >> fields[i];

    if (filesystem::exists(use))
        filesystem::remove_all(use);
    filesystem::create_directory(use);

    generatePOM();
    generateAPI();
    generatePLUGIN();
    generateModelMapper();
    generateMapperConfig();
    generateDynamoDBConfig();
    generateController();
    generateEntity();
    generateApplicationYaml();
    generatePostmanAPIS();

    return 0;
}
