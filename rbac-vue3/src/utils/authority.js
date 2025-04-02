export function checkAuthority(permissionCode, permissions) {
    let hasPermission = true
    if (permissionCode) {
        if (permissionCode instanceof Array && permissionCode.length > 0) {
            hasPermission = permissions.some(permissions => permissionCode.includes(permissions))
        } else {
            hasPermission = permissions.some(item => item == permissionCode)
        }
    }
    return hasPermission
}