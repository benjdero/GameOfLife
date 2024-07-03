import SwiftUI

struct AppIconView: View {
    var body: some View {
        Image(
            uiImage: UIImage(
                named: "AppIcon"
            ) ?? UIImage()
        )
        .resizable()
        .aspectRatio(contentMode: .fit)
        .clipShape(RoundedRectangle(cornerRadius: 32.0))
    }
}

#Preview {
    AppIconView()
        .frame(width: 200.0, height: 200.0)
}
