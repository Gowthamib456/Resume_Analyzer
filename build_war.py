import os
import zipfile
import shutil

PROJECT_DIR = r"C:\Users\user\OneDrive\Desktop\6th sem mini project"
DEPLOY_DIR = os.path.join(PROJECT_DIR, "deploy_careerpath")
WAR_FILE = os.path.join(PROJECT_DIR, "careerpath.war")
TOMCAT_WEBAPPS = r"C:\apache-tomcat-10.1.0\webapps"

print("\n[Step 1] Creating corrected WAR with proper forward slashes...")

# Remove old WAR
if os.path.exists(WAR_FILE):
    os.remove(WAR_FILE)
    print("  ✓ Old WAR removed")

# Create ZIP with proper entry paths (forward slashes)
def create_war_with_forward_slashes(deploy_dir, war_path):
    with zipfile.ZipFile(war_path, 'w', zipfile.ZIP_DEFLATED) as warfile:
        # Walk through deployment directory
        for root, dirs, files in os.walk(deploy_dir):
            for file in files:
                file_path = os.path.join(root, file)
                # Create archive name with forward slashes
                arcname = os.path.relpath(file_path, deploy_dir).replace(os.sep, '/')
                warfile.write(file_path, arcname)
                print(f"    Adding: {arcname}")

try:
    create_war_with_forward_slashes(DEPLOY_DIR, WAR_FILE)
    print("  ✓ WAR created successfully with forward slash paths")
except Exception as e:
    print(f"  ERROR: {e}")
    exit(1)

# Verify WAR contents
print("\n[Step 2] Verifying WAR contents...")
with zipfile.ZipFile(WAR_FILE, 'r') as warfile:
    entries = warfile.namelist()
    web_xml_found = False
    for entry in entries[:30]:  # Show first 30 entries
        if 'web.xml' in entry:
            web_xml_found = True
        print(f"    {entry}")
    
    if web_xml_found:
        print("  ✓ WEB-INF/web.xml found with correct path separator")
    else:
        print("  ✗ WEB-INF/web.xml NOT found - WAR may be corrupted")

# Deploy to Tomcat
print("\n[Step 3] Deploying WAR to Tomcat...")
dest_war = os.path.join(TOMCAT_WEBAPPS, "careerpath.war")
shutil.copy2(WAR_FILE, dest_war)
print(f"  ✓ WAR deployed to {dest_war}")

print("\n✓ WAR creation and deployment complete!")
print(f"\nWAR file: {WAR_FILE}")
print(f"Deploy location: {dest_war}")
