import UIKit

@objc public class ImagePickerController: NSObject, UIImagePickerControllerDelegate, UINavigationControllerDelegate {
    public var onImagePicked: ((Data?) -> Void)?

    // Presents the image picker modally from the given view controller.
    @objc public func presentImagePicker(from viewController: UIViewController) {
        let picker = UIImagePickerController()
        picker.delegate = self
        picker.sourceType = .photoLibrary
        viewController.present(picker, animated: true, completion: nil)
    }

    // Called when the user picks an image.
    public func imagePickerController(_ picker: UIImagePickerController,
                                      didFinishPickingMediaWithInfo info: [UIImagePickerController.InfoKey : Any]) {
        let image = info[.originalImage] as? UIImage
        // Convert image to JPEG data (adjust quality as needed)
        let imageData = image?.jpegData(compressionQuality: 0.8)
        picker.dismiss(animated: true) {
            self.onImagePicked?(imageData)
        }
    }

    // Called when the user cancels picking.
    public func imagePickerControllerDidCancel(_ picker: UIImagePickerController) {
        picker.dismiss(animated: true, completion: nil)
        onImagePicked?(nil)
    }
}