#include <mbgl/util/utf.hpp>

#include <QString>

namespace mbgl {
namespace util {

std::u16string convertUTF8ToUTF16(std::string const& utf8) {
    auto utf16 = QString::fromUtf8(utf8.data(), static_cast<int>(utf8.length()));

    // Newers Qt have QString::toStdU16String(), but this is how it is
    // implemented. Do it here to keep compatibility with older versions.
    return std::u16string(reinterpret_cast<const char16_t*>(utf16.utf16()), utf16.length());
}

} // namespace util
} // namespace mbgl
