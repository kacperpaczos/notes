# Python Package Publishing Guide to PyPI

## Overview
This guide covers the complete process of preparing and publishing a Python package to the Python Package Index (PyPI), including modern best practices using GitHub Actions and trusted publishers.

## Prerequisites
- Python 3.7+ installed
- A GitHub account and repository
- Basic understanding of Python packaging
- Your package code ready for distribution

## 1. Package Structure
Your package should have the following structure:
```
your-package/
├── src/
│   └── your_package/
│       ├── __init__.py
│       └── main.py
├── tests/
├── docs/
├── pyproject.toml
├── README.md
├── LICENSE
├── .gitignore
└── .github/
    └── workflows/
        └── publish.yml
```

## 2. Essential Files

### pyproject.toml
```toml
[build-system]
requires = ["hatchling"]
build-backend = "hatchling.build"

[project]
name = "your-package-name"
version = "0.1.0"
description = "A brief description of your package"
readme = "README.md"
license = {text = "MIT"}
authors = [
    {name = "Your Name", email = "your.email@example.com"}
]
classifiers = [
    "Development Status :: 3 - Alpha",
    "Intended Audience :: Developers",
    "License :: OSI Approved :: MIT License",
    "Programming Language :: Python :: 3",
    "Programming Language :: Python :: 3.7",
    "Programming Language :: Python :: 3.8",
    "Programming Language :: Python :: 3.9",
    "Programming Language :: Python :: 3.10",
    "Programming Language :: Python :: 3.11",
]
requires-python = ">=3.7"
dependencies = [
    "requests>=2.25.0",
]

[project.optional-dependencies]
dev = [
    "pytest>=6.0",
    "black>=21.0",
    "flake8>=3.8",
]

[project.urls]
Homepage = "https://github.com/username/your-package"
Repository = "https://github.com/username/your-package"
Documentation = "https://your-package.readthedocs.io"
```

### README.md
Create a comprehensive README with:
- Package description
- Installation instructions
- Usage examples
- API documentation
- Contributing guidelines
- License information

### LICENSE
Choose an appropriate license (MIT, Apache 2.0, GPL, etc.)

## 3. Local Testing

### Install build tools
```bash
pip install build twine
```

### Build package locally
```bash
python -m build
```

### Test upload to TestPyPI
```bash
# First, create account on https://test.pypi.org
twine upload --repository testpypi dist/*
```

### Install from TestPyPI
```bash
pip install --index-url https://test.pypi.org/simple/ your-package-name
```

## 4. GitHub Actions Setup

### Create .github/workflows/publish.yml
```yaml
name: Publish to PyPI

on:
  release:
    types: [published]

jobs:
  deploy:
    runs-on: ubuntu-latest
    environment:
      name: pypi
      url: https://pypi.org/p/your-package-name
    steps:
    - uses: actions/checkout@v4
    
    - name: Set up Python
      uses: actions/setup-python@v4
      with:
        python-version: '3.11'
    
    - name: Install dependencies
      run: |
        python -m pip install --upgrade pip
        pip install build twine
    
    - name: Build package
      run: python -m build
    
    - name: Publish to PyPI
      env:
        TWINE_USERNAME: __token__
        TWINE_PASSWORD: ${{ secrets.PYPI_API_TOKEN }}
      run: twine upload dist/*
```

## 5. PyPI Trusted Publisher Setup

### Step 1: Configure GitHub Environment
1. Go to your repository → Settings → Environments
2. Create new environment named "pypi"
3. Add protection rules if needed

### Step 2: Add Trusted Publisher on PyPI
1. Go to https://pypi.org/manage/account/publishing/
2. Click "Add new pending publisher"
3. Fill in the form:
   - **PyPI Project Name**: your-package-name
   - **Owner**: your-github-username
   - **Repository name**: your-repository-name
   - **Workflow name**: publish.yml
   - **Environment name**: pypi

### Step 3: Create Release
1. Tag your release in Git:
```bash
git tag v0.1.0
git push origin v0.1.0
```

2. Create GitHub release from the tag
3. The workflow will automatically trigger and publish to PyPI

## 6. Alternative: Manual Upload with API Token

If you prefer manual uploads:

### Create API Token
1. Go to https://pypi.org/manage/account/token/
2. Create new token with scope "Entire account (all projects)"
3. Copy the token

### Upload manually
```bash
twine upload dist/*
# Enter username: __token__
# Enter password: pypi-<your-token>
```

## 7. Best Practices

### Version Management
- Use semantic versioning (MAJOR.MINOR.PATCH)
- Consider using tools like `bump2version` for automated versioning
- Never re-upload the same version

### Security
- Use trusted publishers instead of API tokens when possible
- Never commit secrets to your repository
- Use GitHub environments for additional security

### Quality Assurance
- Write comprehensive tests
- Use pre-commit hooks for code quality
- Run tests before publishing
- Check your package with `twine check dist/*`

### Documentation
- Keep README up to date
- Include usage examples
- Document all public APIs
- Consider using Sphinx for detailed documentation

## 8. Troubleshooting

### Common Issues
- **Build fails**: Check `pyproject.toml` syntax and dependencies
- **Upload fails**: Verify PyPI credentials and package name availability
- **Import errors**: Ensure `__init__.py` files are present
- **Version conflicts**: Check if version already exists on PyPI

### Useful Commands
```bash
# Check package structure
python -m build --sdist --wheel

# Validate package
twine check dist/*

# Test installation
pip install --force-reinstall dist/*.whl

# Clean build artifacts
rm -rf dist/ build/ *.egg-info/
```

## 9. Post-Publication

### Verify Installation
```bash
pip install your-package-name
python -c "import your_package; print('Success!')"
```

### Monitor
- Check PyPI page for your package
- Monitor download statistics
- Respond to issues and pull requests
- Keep dependencies updated

## 10. Resources

- [Python Packaging User Guide](https://packaging.python.org/)
- [PyPI Documentation](https://pypi.org/help/)
- [GitHub Actions Documentation](https://docs.github.com/en/actions)
- [Hatchling Build Backend](https://hatch.pypa.io/latest/)
- [Twine Documentation](https://twine.readthedocs.io/)

## Summary
This guide covers the modern approach to Python package publishing using:
1. **pyproject.toml** for package configuration
2. **GitHub Actions** for automated publishing
3. **Trusted Publishers** for secure, credential-free deployment
4. **Best practices** for package quality and security

The trusted publisher approach eliminates the need for API tokens and provides a more secure, automated publishing workflow.
