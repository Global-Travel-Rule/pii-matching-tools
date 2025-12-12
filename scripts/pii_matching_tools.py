#  Copyright (c) 2022-2025 Global Travel Rule • globaltravelrule.com
#  License that can be found in the LICENSE file.
#  Author: Global Travel Rule developer
#  Created on: 2025/12/12 11:26
import re
from enum import Enum
from typing import List, Dict


class NameField(Enum):
    """Name field enumeration"""
    FIRST_NAME = "first_name"
    MIDDLE_NAME = "middle_name"
    LAST_NAME = "last_name"


class NameValidator:
    # Permitted characters: letters, spaces, hyphens, apostrophes (commonly used in international names)
    NAME_PATTERN = r'^[A-Za-zÀ-ÿ\s\'-]+$'

    # Special character check
    SPECIAL_CHARS_PATTERN = r'[^A-Za-zÀ-ÿ\s\'-]'

    @staticmethod
    def validate_single_field(field: str, field_type: NameField) -> Dict:
        """
        Verify a single name field

        Args:
            field: String to be verified
            field_type: field type

        Returns:
            Dict: Dictionary containing validation results and error information
        """
        result = {
            "is_valid": False,
            "field_type": field_type.value,
            "original_value": field,
            "clean_value": None,
            "errors": []
        }

        # Handle null values
        if field is None:
            result["errors"].append("字段不能为None")
            return result

        field = str(field).strip()

        # Empty string check
        if not field:
            if field_type == NameField.MIDDLE_NAME:
                # The middle name can be empty
                result["is_valid"] = True
                result["clean_value"] = ""
                return result
            else:
                result["errors"].append(f"{field_type.value} must not be null")
                return result

        # Length check
        if len(field) < 1:
            result["errors"].append(f"{field_type.value} must not be empty string")
            return result

        if len(field) > 50:
            result["errors"].append(f"{field_type.value} length cannot exceed 50 characters")

        # Special character check
        special_chars = re.findall(NameValidator.SPECIAL_CHARS_PATTERN, field)
        if special_chars:
            unique_chars = set(special_chars)
            result["errors"].append(f"Contains special characters that are not allowed: {', '.join(unique_chars)}")

        # Continuous special character check (multiple spaces are considered as one)
        if re.search(r"[\s\-']{2,}", field):
            result["errors"].append("Contains consecutive special characters")

        # First and last character check
        if field[0] in " \'-":
            result["errors"].append("Cannot start with a space, hyphen, or apostrophe")

        if field[-1] in " \'-":
            result["errors"].append("Cannot end with a space, hyphen, or apostrophe")

        # If all checks pass
        if not result["errors"]:
            result["is_valid"] = True
            result["clean_value"] = field

        return result

    @staticmethod
    def validate_name_list(name_list: List[List[str]]) -> List[Dict]:
        """
        Batch Validation Name List

        Args:
            name_list: List[List[str]]，Each inner list contains 3 strings

        Returns:
            List[Dict]: Verification results for each name
        """
        results = []

        for i, name_fields in enumerate(name_list):
            # Ensure that each name has 3 fields
            if len(name_fields) != 3:
                field_count = len(name_fields)
                results.append({
                    "index": i,
                    "is_valid": False,
                    "errors": [f"Incorrect number of word fields: Expected 3, actual {field_count}"],
                    "fields": []
                })
                continue

            first_name, middle_name, last_name = name_fields

            # Verify each field
            first_result = NameValidator.validate_single_field(
                first_name, NameField.FIRST_NAME
            )
            middle_result = NameValidator.validate_single_field(
                middle_name, NameField.MIDDLE_NAME
            )
            last_result = NameValidator.validate_single_field(
                last_name, NameField.LAST_NAME
            )

            # Summary results
            all_valid = (
                    first_result["is_valid"] and
                    last_result["is_valid"] and
                    (middle_result["is_valid"] or middle_name == "")
            )

            # Collect all errors
            all_errors = []
            for result in [first_result, middle_result, last_result]:
                all_errors.extend(result.get("errors", []))

            results.append({
                "index": i,
                "is_valid": all_valid,
                "errors": all_errors,
                "fields": [
                    first_result,
                    middle_result,
                    last_result
                ]
            })

        return results


class NameStandardizer:
    """Name Standardization Processor"""

    @staticmethod
    def standardize_field(field: str) -> str:
        """Standardize individual fields"""
        if not field:
            return ""

        # Remove spaces at both ends
        field = field.strip()

        # Replace multiple spaces with a single space
        field = re.sub(r'\s+', ' ', field)

        # Standardize spaces around hyphens and apostrophes
        field = re.sub(r'\s*-\s*', '-', field)
        field = re.sub(r"\s*'\s*", "'", field)

        # Intelligent case handling
        return NameStandardizer._smart_case(field)

    @staticmethod
    def _smart_case(field: str) -> str:
        """Intelligent case conversion"""
        # If it is already mixed case (such as McDonald, O'Connor), keep it as it is
        if re.search(r'[a-z]', field) and re.search(r'[A-Z]', field):
            # Check for any special circumstances (such as all capitalized abbreviations)
            if field.isupper():
                # Handle all capitalized abbreviations
                parts = []
                for word in field.split():
                    if len(word) <= 2:
                        parts.append(word.upper())  # Abbreviations should be capitalized
                    else:
                        parts.append(word.title())
                return ' '.join(parts)
            return field

        # For all lowercase or all capitalized, apply intelligent title processing
        def process_word(w: str) -> str:
            if '-' in w:
                # Processing hyphenated words
                return '-'.join(process_word(part) for part in w.split('-'))
            elif "'" in w:
                # Processing apostrophe words
                return "'".join(process_word(part) for part in w.split("'"))
            elif w.upper() in ['VAN', 'VON', 'DE', 'LA', 'MC', 'MAC']:
                # Special prefix processing
                return w.title()
            elif len(w) <= 2:
                # Keep short words as they are (possibly abbreviations)
                return w.upper()
            else:
                # Capitalize the first letter of a regular word
                return w.title()

        # Process each word
        words = field.split()
        processed_words = [process_word(word) for word in words]
        return ' '.join(processed_words)

    @staticmethod
    def standardize_name_list(name_list: List[List[str]]) -> List[List[str]]:
        """Standardize the entire list of names"""
        standardized_list = []

        for name_fields in name_list:
            if len(name_fields) != 3:
                # If the number of fields is incorrect, skip or fill in default values
                continue

            standardized_fields = [
                NameStandardizer.standardize_field(name_fields[0]),  # first
                NameStandardizer.standardize_field(name_fields[1]),  # middle
                NameStandardizer.standardize_field(name_fields[2])  # last
            ]
            standardized_list.append(standardized_fields)

        return standardized_list


# Python Usage example
def main():
    # test data
    test_names = [
        ["John", "", "Doe"],  # valid
        ["Mary", "Jane", "Smith-Jones"],  # valid
        ["Robert", "O'Conner", "McDonald"],  # valid
        ["David", "Lee", "Roth@"],  # invalid (contains @）
        ["Sarah", "  ", "Wilson"],  # valid（Middle name only has spaces）
        ["", "", ""],  # invalid（First and last names cannot be empty）
        ["Anne-Marie", "", "de la Cruz"],  # valid
        ["Mike", "T.", "O'Neil"]  # valid
    ]

    # Verify Name
    print("=== Verify Name ===")
    validation_results = NameValidator.validate_name_list(test_names)

    for result in validation_results:
        print(f"record {result['index']}: ", end="")
        if result["is_valid"]:
            print("✓ valid")
            fields = result["fields"]
            print(f"  first name: '{fields[0]['clean_value']}'")
            print(f"  middle name: '{fields[1]['clean_value']}'")
            print(f"  family name: '{fields[2]['clean_value']}'")
        else:
            print("✗ invalid - Error:", ", ".join(result["errors"]))
        print()

    # Standardized name
    print("=== Standardized name ===")
    standardized_names = NameStandardizer.standardize_name_list(test_names)

    for i, name in enumerate(standardized_names):
        print(f"record {i}: {name}")


if __name__ == "__main__":
    main()
